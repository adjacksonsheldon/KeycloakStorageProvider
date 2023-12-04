# KeycloakStorageProvider

O **KeycloakStorageProvider** é um projeto desenvolvido para criar um componente personalizado que pode ser integrado ao Keycloak. Este componente atua como um provedor de armazenamento remoto, permitindo a integração com serviços externos para armazenamento de dados.

## Como Rodar o Projeto

O principal objetivo deste repositório é oferecer uma solução flexível e adaptável para armazenar dados externos utilizados pelo Keycloak. Ele serve como uma extensão que se integra harmoniosamente ao sistema Keycloak, permitindo a recuperação e o armazenamento eficiente de informações relevantes para a autenticação e autorização.

## Como Rodar o Projeto

### Pré-requisitos
Certifique-se de ter o Docker e o Docker Compose instalados em seu ambiente.

### Passos para Execução

1. Execute o comando **mvn package**
2. A variável de ambiente REMOTE_SERVICE_URI é crucial para a integração bem-sucedida com o serviço externo. Certifique-se de defini-la corretamente antes de iniciar o Keycloak. Ela deve correponder ao host/porta que o projeto [KeycloakExternalRepository](https://github.com/adjacksonsheldon/KeycloakExternalRepository) está sendo executado
3. Execute o Docker Compose para implantar o componente como um módulo dentro do Keycloak: **docker-compose up -d** 

Para mais informações consulte a documentação do projeto [KeycloakExternalRepository](https://github.com/adjacksonsheldon/KeycloakExternalRepository).