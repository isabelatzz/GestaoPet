🐾 GestaoPet
Projeto em andamento para gestão de pet shops com interface gráfica em Java Swing.

📌 Sobre o Projeto
O GestaoPet é um sistema de gerenciamento para pet shops que está sendo desenvolvido com Java para desktop, utilizando Java Swing para a construção da interface gráfica. O objetivo do projeto é facilitar o controle de animais, tutores, serviços e geração de recibos em estabelecimentos veterinários e de cuidados animais.

Este repositório é o ponto de partida para o projeto e ainda está em fase inicial.

🧱 Estrutura Inicial
O projeto já conta com uma interface (PetLoverInterface) que define os principais métodos necessários para gerenciar as entidades básicas do sistema:

📂 Pacote: ProjetoPetShop.entities
Interface: PetLoverInterface
Métodos implementados ou planejados:

🐶 Animais
cadastrarAnimal(Animal animal)

buscarAnimalPorID(int id)

listarAnimaisPorTutor(String cpf)

removerAnimal(int id)

listarTodosAnimais()

atualizarAnimal(Animal animal)

👤 Tutores
cadastrarTutor(Tutor tutor)

buscarTutorPorCpf(String cpf)

listarTodosTutores()

🧾 Recibos
gerarRecibo(int idServico)

imprimirRecibo(int idServico)

listarServicosPendentes()

🛠️ Funcionalidades Futuras (Planejadas)
java
Copiar
Editar
void agendarConsulta(Consulta consulta);
void agendarBanho(Banho banho);
void agendarTosa(Tosa tosa);
Servico buscarServico(int id);
void atualizarServico(Servico servico);
void cancelarServico(int id);
List<Servico> listarServicosPorAnimal(int idAnimal);
List<Servico> listarServicosPorTutor(String cpfTutor);
🚧 Status do Projeto
 Definição da interface principal

 Implementação das classes Animal, Tutor, Servico, entre outras

 Criação da interface gráfica com Java Swing

 Integração das funcionalidades com o front-end

 Testes e validações

📌 Objetivo
Criar uma aplicação prática e funcional para gestão de pet shops, visando organização, agilidade e facilidade de uso para os funcionários e administradores desses estabelecimentos.

🛠️ Tecnologias Utilizadas
Java 17+

Java Swing (GUI)

Padrões de Projeto (Interface, MVC - a definir)

📅 Em breve...
Mais funcionalidades e melhorias virão com o avanço do projeto. Acompanhe este repositório para atualizações! 💻🐾

