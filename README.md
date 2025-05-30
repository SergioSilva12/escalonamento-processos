/* 
============================================
  SISTEMAS OPERACIONAIS - PROJETO
============================================

Equipe:
- Evilyn Pimenta de Sousa
- Guilherme Oliveira Camilo
- João Antonio da Silva Satiro
- João Pedro Lima Leite Neto
- Sérgio Silva de Oliveira

============================================
ÍNDICE
1. Objetivo do projeto
2. Algoritmo de Escalonamento Base
3. Algoritmo de Escalonamento Adicional (Sistemas Interativos)
4. Simulações com Conjunto de Requisições Aleatórias
5. Escalonamento com Threads
============================================
*/

/* 
============================================
1. OBJETIVO
============================================
Implementar e analisar diferentes algoritmos de escalonamento de processos 
em sistemas operacionais, com foco no:

- Shortest Job First (SJF) como algoritmo base.
- Round Robin (RR) ou First Come First Served (FCFS) como algoritmos adicionais 
  para sistemas interativos.
*/

/* 
============================================
2. ALGORITMO DE ESCALONAMENTO BASE - SJF
============================================
- Algoritmo não preemptivo.
- Seleciona o processo com menor tempo de execução (burst time).
- Calcula:
    * Tempo de espera (waiting time).
    * Tempo de retorno (turnaround time).
- Minimiza o tempo médio de espera, priorizando processos mais curtos.

Tecnologias:
- Linguagem C (uso de structs e arrays).
- Linguagem Python (uso de classes e listas).
*/

/*
============================================
3. ALGORITMO DE ESCALONAMENTO ADICIONAL - RR
============================================
- Algoritmo preemptivo para sistemas interativos.
- Cada processo recebe uma fatia de tempo fixa (quantum).
- Garante:
    * Justiça na distribuição do tempo.
    * Responsividade para os processos.

Tecnologias:
- Linguagem C.
- Linguagem Python.
*/

/*
============================================
4. SIMULAÇÕES COM CONJUNTO DE REQUISIÇÕES ALEATÓRIAS
============================================
- Geração de processos com burst times aleatórios.
- Comparação entre SJF e RR com os mesmos processos.
- Métricas avaliadas:
    * Tempo médio e máximo de espera.
    * Tempo médio e máximo de turnaround.
*/

/*
============================================
5. ESCALONAMENTO COM THREADS
============================================
- Simulações utilizando múltiplas threads: 2, 4 e 6 threads.
- Objetivo: avaliar o desempenho concorrente.
- Utilização de:
    * Biblioteca threading (em Python).
    * Biblioteca ExecutorService (em Java/C++ opcionalmente).
*/

/*
============================================
CÓDIGOS IMPLEMENTADOS EM:
- C
- Python

Repositório:
https://github.com/SergioSilva12/escalonamento-processos
============================================
*/