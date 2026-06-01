# Changelog

## [0.1.0] - 2026-06-01

### Adicionado
- **Cadastro de Usuários (Issue 0):** Implementação do modelo de Usuário com campos obrigatórios e opcionais, criptografia simulada de senha, validação de unicidade de CPF/E-mail e atribuição automática dos perfis `ROLE_COORDENADOR` e `ROLE_AVALIADOR`.

- **Gerenciamento de Editais (Issue 1):** Criação do fluxo de cadastro e listagem de editais com validação automatizada de datas (garantindo que a data de início seja anterior à data de fim).

- **Infraestrutura:** Configuração do pipeline de Integração Contínua (CI) com GitHub Actions (`maven.yml`) utilizando Java 17 e Maven para execução de testes.