```markdown
# Sistema Acadêmico - POO

Projeto desenvolvido como requisito universitário para a disciplina de Programação Orientada a Objetos (POO) do curso de Análise e Desenvolvimento de Sistemas (UNICID). 

O sistema é uma aplicação backend simples com persistência em banco de dados para gerenciar o cadastro de alunos, cursos e o registro acadêmico.

## ⚙️ Funcionalidades
* Cadastro de Cursos (incluindo campus e período).
* Cadastro de Alunos com validação de vínculo ao curso.
* Registro de Notas e Faltas por disciplina e semestre.
* Operações básicas de CRUD integradas ao banco de dados.

## 🛠️ Tecnologias Utilizadas
* **Linguagem:** Java
* **Banco de Dados:** MySQL
* **Integração:** JDBC (Java Database Connectivity)
* **IDE:** Eclipse

## 🚀 Como executar o projeto localmente

1. Clone este repositório na sua máquina:
   ```bash
   git clone [https://github.com/Sandroluan/sistema-academico-POO.git](https://github.com/Sandroluan/sistema-academico-POO.git)

```

2. Importe o projeto para a sua workspace do Eclipse.
3. Configure o Banco de Dados:
* Abra o seu MySQL e crie o banco de dados chamado `sistema_academico_POO`.
* Execute os scripts de criação das tabelas (`tb_curso`, `tb_alunos`, `tb_notas_faltas`).
* **Importante:** Insira alguns dados iniciais na tabela `tb_curso` antes de tentar cadastrar alunos para evitar erros de Chave Estrangeira.


4. Configure a Conexão:
* Localize a classe de conexão no código Java.
* Certifique-se de que a URL (`jdbc:mysql://localhost:3306/sistema_academico_POO?useSSL=false&serverTimezone=America/Sao_Paulo`) está correta.
* Altere o `user` e o `password` para as suas credenciais locais do MySQL.


5. Execute a classe principal no Eclipse.

## 🧑‍💻 Autor

Luan

```

```
