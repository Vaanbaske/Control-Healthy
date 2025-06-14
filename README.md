Autores:

Alex Geymeson Lemos de Araujo - RA: 82519534
Arthur de Assis Matos - RA: 825141669
Kauann Dos Santos Silva - RA: 825141522
Paulo Barreiro - RA: 825161684
Victor Sousa de Carvalho - RA: 820266034
Vinicius Paiutti - RA: 824216626
Professor Orientador:

Tulio Cearamicoli Vivaldini

Control Healthy - Sistema de Monitoramento de Saúde
O Control Healthy é uma aplicação desktop para o monitoramento de pressão arterial, desenvolvida como projeto para a disciplina de Programação de Soluções Computacionais. 

Sobre o Projeto
Contribuição para a Saúde (ODS 3)

Autores e Orientador
Sobre o Projeto
Este sistema foi criado para auxiliar no monitoramento e gestão da pressão arterial de pacientes.  A hipertensão é uma condição crônica que exige acompanhamento contínuo, e a falta de ferramentas adequadas pode dificultar a avaliação de tratamentos e a prevenção de complicações graves como doenças cardíacas e acidentes vasculares cerebrais. 

O Control Healthy oferece uma solução desktop robusta onde profissionais de saúde e pacientes podem registrar, visualizar e analisar dados de saúde de forma eficiente. 

Contribuição para a Saúde (ODS 3)
O projeto está alinhado ao Objetivo de Desenvolvimento Sustentável (ODS) 3 da ONU: Saúde e Bem-Estar.  Ele contribui ao:

Apoiar a Gestão de Doenças Crônicas: Facilitando o monitoramento contínuo da hipertensão.
Permitir a Tomada de Decisão Baseada em Dados: Gerando gráficos e exportando dados para análise profissional.
Empoderar e Educar o Paciente: A visualização gráfica ajuda o paciente a compreender sua condição e a importância do tratamento.
Melhorar a Qualidade de Vida: Auxiliando na prevenção de complicações graves associadas à pressão alta.
Principais Funcionalidades
Autenticação de Usuários: Tela de login segura para garantir o acesso restrito ao sistema.
Gerenciamento Completo (CRUD):
Usuários: Adição, listagem, edição e exclusão de usuários do sistema (pacientes, médicos, administradores).
Pacientes: Cadastro dedicado para pacientes com suas informações essenciais.
Registro de Pressão Arterial: Interface para registrar medições de pressão sistólica e diastólica associadas a uma data.
Visualização Gráfica: Geração de gráficos de linha (diários, semanais e mensais) para analisar a evolução da pressão arterial ao longo do tempo.
Exportação para Excel: Funcionalidade para exportar os registros de pressão para arquivos .xlsx, com opção de filtro por intervalo de datas.
Navegação Simplificada: Um menu principal centraliza o acesso a todas as funcionalidades, proporcionando uma experiência de uso fluida.
Stack Tecnológico
O sistema foi construído utilizando as seguintes tecnologias e ferramentas:

Linguagem: Java (versão 21)
Interface Gráfica: Java Swing
Banco de Dados: MySQL
Ambiente de BD: XAMPP
IDE de Desenvolvimento: Eclipse
Gerenciador de Dependências: Maven
Bibliotecas Externas:
JFreeChart: Para a geração de gráficos.
Apache POI: Para a exportação de dados para Excel.
FlatLaf: Para modernizar a aparência da interface (Look and Feel).
MySQL Connector/J: Driver de conexão com o banco de dados.
Como Executar
Pré-requisitos
Antes de começar, garanta que você tenha os seguintes softwares instalados:

Java Development Kit (JDK) - Versão 21 ou superior.
XAMPP - Para gerenciar o banco de dados MySQL.
Uma IDE Java como Eclipse ou IntelliJ IDEA.
Apache Maven.

Estrutura do Projeto
O código-fonte (src) está organizado em pacotes Java que seguem uma arquitetura em camadas:

br.com.saude.dao: Data Access Objects - Classes que gerenciam a comunicação com o banco de dados (CRUD).
br.com.saude.model: Modelos - Classes que representam as entidades do sistema (ex: Paciente, Usuario).
br.com.saude.ui: User Interface - Todas as telas do sistema desenvolvidas com Java Swing.
br.com.saude.util: Utilitários - Classes de apoio, como a de conexão com o banco de dados e de exportação para Excel.
br.com.saude.old: Código Legado - Versões antigas e classes de teste do desenvolvimento inicial.


Este projeto foi desenvolvido por estudantes da Universidade São Judas Tadeu - Campus Paulista. 

