Instalando o Glassfish

1. Entrar no diretorio glassfish3\glassfish\domains\domain1
2. Remover os conteudos dos diretorios
applications
autodeploy
eclipseApps
generated
osgi-cache
3. Configurar o datasource no diretorio config/domain.xml

Criando um CRUD

Tela de Listagem
----------------

Tela de Cadastro
----------------
1. Criar o EditPanel
2. No ListPanel apontar o EditPanel
3. Copiar modelo de EditPanel (EditDispositivoPanel)
4. Copiar modelo de EditPanel html
5. Colocar fields no método addFormFields
6. Implementar os métodos do CRUD
7. Fazer a fachada
8. Fazer o DAO
9. Fazer o Entity
10. Colocar no BasePage.properties as chaves de texto 