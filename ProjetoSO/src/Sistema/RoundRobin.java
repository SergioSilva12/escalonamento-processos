package Sistema;

import java.util.*;
import java.util.concurrent.*;

class ProcessoRR {
    int pid;
    int burst;
    int remainingBurst;  // Tempo restante de execução (atualizado durante a simulação)
    long startTime = -1; // Marcadores de tempo para cálculos de espera/turnaround
    long endTime = -1;

    public ProcessoRR(int pid, int burst) {
        this.pid = pid;
        this.burst = burst;
        this.remainingBurst = burst;
    }
}

public class RoundRobin {

    // Método principal: roda simulações com 2, 4 e 6 threads
    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        int quantum = 2;
        List<ProcessoRR> processos = new ArrayList<>();
        Random rand = new Random();

        // Gera processos aleatórios
        for (int i = 0; i < n; i++) {
            processos.add(new ProcessoRR(i + 1, rand.nextInt(10) + 1));
        }

        // Testa com diferentes números de threads
        runSimulation(2, new ArrayList<>(processos), quantum);
        runSimulation(4, new ArrayList<>(processos), quantum);
        runSimulation(6, new ArrayList<>(processos), quantum);
    }

    // Simulação Round Robin com X threads
    public static void runSimulation(int numThreads, List<ProcessoRR> processos, int quantum)
            throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // Pool de threads
        Queue<ProcessoRR> fila = new LinkedList<>(processos); // Fila de processos prontos
        long inicioSimulacao = System.currentTimeMillis(); // Tempo de início da simulação

        System.out.println("\nRound Robin com " + numThreads + " threads:");

        while (!fila.isEmpty()) {
            ProcessoRR p = fila.poll();

            if (p.startTime == -1) {
                p.startTime = System.currentTimeMillis(); // Marca o início da execução
            }

            int tempoExec = Math.min(p.remainingBurst, quantum); // Executa o quantum ou o restante
            p.remainingBurst -= tempoExec;

            // Simula execução em uma thread do pool
            CountDownLatch latch = new CountDownLatch(1);
            executor.submit(() -> {
                try {
                    Thread.sleep(tempoExec * 500); // Simula tempo de CPU (meio segundo por unidade de burst)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
            latch.await(); // Espera a thread terminar

            if (p.remainingBurst > 0) {
                fila.add(p); // Reinsere na fila se não terminou
            } else {
                p.endTime = System.currentTimeMillis(); // Marca o fim do processo
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Calcula métricas de desempenho
        calcularEstatisticas(processos, inicioSimulacao, numThreads);
    }

    // Exibe Waiting Time e Turnaround Time médios
    private static void calcularEstatisticas(List<ProcessoRR> processos, long inicioSimulacao, int numThreads) {
        double totalWaiting = 0;
        double totalTurnaround = 0;
        int n = processos.size();

        System.out.println("\nResultados para " + numThreads + " threads:");
        for (ProcessoRR p : processos) {
            long turnaround = p.endTime - inicioSimulacao;
            long waiting = turnaround - (p.burst * 500); // Waiting = Turnaround - Tempo de CPU
            totalWaiting += waiting;
            totalTurnaround += turnaround;
        }

        System.out.printf("Média Waiting Time: %.2f ms\n", totalWaiting / n);
        System.out.printf("Média Turnaround Time: %.2f ms\n", totalTurnaround / n);
    }
}