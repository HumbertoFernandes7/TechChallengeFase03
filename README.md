# 🚀 Tech Challenge Fase 3 - Sistema de Agendamento Hospitalar

Este projeto é a solução para o Tech Challenge da Fase 3, implementando um sistema de backend modular para um ambiente hospitalar, com foco em segurança, microsserviços e comunicação assíncrona.

O sistema é construído como um monorepo contendo dois serviços principais:
1.  **`agendamento-service`**: A API principal (REST e GraphQL) responsável pelo gerenciamento de usuários (Pacientes, Médicos, Enfermeiros) e pelo agendamento de consultas.
2.  **`notificacoes-service`**: Um serviço de background que ouve eventos de agendamento e envia lembretes por e-mail aos pacientes.

---

## 🛠️ Tecnologias e Arquitetura

Este projeto utiliza uma arquitetura de microsserviços orquestrada com Docker Compose.

* **Linguagem:** Java 21 e Spring Boot 3.5.7
* **Banco de Dados:** MySQL 8.0
* **Segurança:** Spring Security com autenticação JWT.
* **API:** REST e GraphQL (para consultas de histórico).
* **Comunicação Assíncrona:** RabbitMQ.
* **Envio de E-mail:** Brevo (via SMTP).
* **Orquestração:** Docker e Docker Compose.

### Arquitetura de Microsserviços


1.  Um usuário (Médico/Enfermeiro) cria uma consulta via API REST no **`agendamento-service`**.
2.  O **`agendamento-service`** salva a consulta no banco **MySQL** e, simultaneamente, publica uma mensagem (evento) na fila do **RabbitMQ**.
3.  O **`notificacoes-service`**, que está ouvindo a fila, consome a mensagem.
4.  O **`notificacoes-service`** processa a mensagem e usa o serviço Brevo para enviar um e-mail de confirmação/lembrete ao paciente.

---

## 🚀 Como Executar (Instruções)



Este projeto é 100% containerizado. A única dependência é ter o **Docker** e o **Docker Compose** instalados.

1.  Clone este repositório.
2.  Abra um terminal na pasta raiz do projeto (ex: `/TechChallengeFase3`).
3.  Execute o seguinte comando:

```bash
docker-compose up --build