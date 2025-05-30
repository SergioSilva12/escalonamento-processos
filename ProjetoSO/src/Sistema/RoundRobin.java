package Sistema;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class RoundRobin extends JFrame {

    private JTextArea outputArea;
    private JButton startButton;
    private JComboBox<Integer> threadSelector;
    private JProgressBar progressBar; // Barra de progresso adicionada

    public RoundRobin() {
        setTitle("Simulador Round Robin com Progresso");
        setSize(600, 450); // Aumentei a altura para acomodar a barra de progresso
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de controles (topo)
        JPanel controlPanel = new JPanel();
        startButton = new JButton("Iniciar Simulação");
        Integer[] threadOptions = {2, 4, 6};
        threadSelector = new JComboBox<>(threadOptions);

        controlPanel.add(new JLabel("Threads:"));
        controlPanel.add(threadSelector);
        controlPanel.add(startButton);

        // Área de saída de texto (centro)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Barra de progresso (embaixo)
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Mostra porcentagem
        progressBar.setString("Pronto para iniciar");

        // Adiciona componentes à janela
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH); // Barra de progresso na parte inferior

        startButton.addActionListener(e -> {
            int selectedThreads = (int) threadSelector.getSelectedItem();
            new Thread(() -> runSimulation(selectedThreads)).start(); // Executa em thread separada
        });
    }

    private void runSimulation(int numThreads) {
        startButton.setEnabled(false); // Desabilita botão durante simulação
        progressBar.setValue(0);
        progressBar.setString("Iniciando...");

        try {
            int n = 10;
            int quantum = 2;
            List<ProcessoRR> processos = new ArrayList<>();
            Random rand = new Random();

            // Gera processos
            for (int i = 0; i < n; i++) {
                processos.add(new ProcessoRR(i + 1, rand.nextInt(10) + 1));
            }

            // Calcula o total de trabalho para a barra de progresso
            int totalBurst = processos.stream().mapToInt(p -> p.burst).sum();
            int completedBurst = 0;

            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            Queue<ProcessoRR> fila = new LinkedList<>(processos);
            long inicioSimulacao = System.currentTimeMillis();

            while (!fila.isEmpty()) {
                ProcessoRR p = fila.poll();

                if (p.startTime == -1) {
                    p.startTime = System.currentTimeMillis();
                }

                int tempoExec = Math.min(p.remainingBurst, quantum);
                p.remainingBurst -= tempoExec;

                // Atualiza GUI
                SwingUtilities.invokeLater(() -> {
                    outputArea.append(String.format(
                            "Processo %d: Executando %d unidades (Restante: %d)\n",
                            p.pid, tempoExec, p.remainingBurst
                    ));
                });

                CountDownLatch latch = new CountDownLatch(1);
                executor.submit(() -> {
                    try {
                        Thread.sleep(tempoExec * 500); // Simula trabalho
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
                latch.await();

                // Atualiza progresso
                completedBurst += tempoExec;
                int progress = (int) (((double) completedBurst / totalBurst) * 100);
                SwingUtilities.invokeLater(() -> {
                    progressBar.setValue(progress);
                    progressBar.setString(String.format("%d%% completado", progress));
                });

                if (p.remainingBurst > 0) {
                    fila.add(p);
                } else {
                    p.endTime = System.currentTimeMillis();
                }
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            // Resultados finais
            SwingUtilities.invokeLater(() -> {
                calcularEstatisticas(processos, inicioSimulacao, numThreads);
                progressBar.setString("Simulação concluída!");
                startButton.setEnabled(true);
            });

        } catch (InterruptedException ex) {
            SwingUtilities.invokeLater(() -> {
                outputArea.append("Erro: " + ex.getMessage());
                progressBar.setString("Erro na simulação");
            });
        }
    }

    private void calcularEstatisticas(List<ProcessoRR> processos, long inicioSimulacao, int numThreads) {
        double totalWaiting = 0;
        double totalTurnaround = 0;
        int n = processos.size();

        outputArea.append("\n=== RESULTADOS ===\n");
        for (ProcessoRR p : processos) {
            long turnaround = p.endTime - inicioSimulacao;
            long waiting = turnaround - (p.burst * 500);
            totalWaiting += waiting;
            totalTurnaround += turnaround;
        }

        outputArea.append(String.format(
                "Threads usadas: %d\nMédia Waiting Time: %.2f ms\nMédia Turnaround Time: %.2f ms\n",
                numThreads, totalWaiting / n, totalTurnaround / n
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoundRobin gui = new RoundRobin();
            gui.setVisible(true);
        });
    }
}

class ProcessoRR {
    int pid;
    int burst;
    int remainingBurst;
    long startTime = -1;
    long endTime = -1;

    public ProcessoRR(int pid, int burst) {
        this.pid = pid;
        this.burst = burst;
        this.remainingBurst = burst;
    }
}