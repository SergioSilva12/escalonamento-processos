Projeto de Algoritmos de Escalonamento - Sistemas Operacionais
Equipe
Evilyn Pimenta de Sousa

João Pedro Lima Leite Neto

Sérgio Silva de Oliveira

João Antonio da Silva Satiro

Guilherme Oliveira Camilo

Descrição do Projeto
Este projeto tem como objetivo a implementação, simulação e análise de algoritmos de escalonamento de processos em sistemas operacionais, com foco em algoritmos clássicos e sistemas interativos.

Algoritmos Implementados:
Shortest Job First (SJF) (Algoritmo base)

Round Robin (RR) (Algoritmo para sistemas interativos)

Os algoritmos foram implementados nas linguagens C e Python para fins comparativos.

Estrutura do Projeto
1. Implementação do algoritmo SJF (Shortest Job First)
Algoritmo não-preemptivo que seleciona o processo com menor tempo de execução (burst time) para rodar primeiro.

Calcula tempo de espera (waiting time) e tempo de turnaround (turnaround time) para cada processo.

Minimiza o tempo médio de espera, privilegiando processos mais curtos.

Tecnologias:

Linguagem C (uso de structs e arrays)

Linguagem Python (uso de classes e listas)

2. Implementação do algoritmo Round Robin (RR)
Algoritmo preemptivo para sistemas interativos.

Cada processo recebe uma fatia de tempo fixa (quantum) para execução antes de ser interrompido e reprogramado.

Garante justiça e responsividade para processos.

Tecnologias:

Linguagem C

Linguagem Python

3. Simulações com conjuntos de requisições aleatórias
Geração de processos com burst times aleatórios para testes e análise.

Comparação dos algoritmos SJF e RR usando os mesmos processos para avaliação justa.

Métricas calculadas:

Tempo médio e máximo de espera

Tempo médio e máximo de turnaround

4. Implementação de multithreading
Execução das simulações utilizando 2, 4 e 6 threads para avaliar desempenho concorrente.

Utilização da biblioteca threading em Python para paralelismo.

