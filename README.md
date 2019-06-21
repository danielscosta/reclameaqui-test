
# ReclameaquiTest


Serviço desenvolvido como resolução de teste promovido no processo seletivo para a empresa Reclame Aqui
Tecnologias Utilizadas:

- kotlin
- spring boot
- swagger
- mongodb
- docker
- docker-compose

Funcionalidade com Geo Localização Implementadas:

- /complains/point/city/{latitude}/{longitude} : busca reclamações na cidade da localização do ponto fornecido
- /complains/point/{latitude}/{longitude}/{distance} : busca reclamações distantes de X Km a partir de um ponto fornecido

# Para executar localmente

- gradle clean build
- sudo docker-compose up --build

\* Documentação da Api (http://localhost:8080/swagger-ui.html)

# Sugestão de roteiro de deploy

Criar uma instância produtiva do Mongo DB e o Index necessário (*db.complain.createIndex({"locale.point": "2dsphere"}))
Criar ConfigMap no K8S definindo as variáveis de ambiente:

- ACTIVE_PROFILE:prod
- GOOGLE_API_KEY: -- Chave de aceso para Api do Google --
- MONGO_HOST: -- Host de acesso Mongo DB --
- MONGO_PORT: -- Porta de acesso ao Mongo DB --
- MONGO_DB: -- Database Mongo DB --
- MONGO_USERNAME: -- Usuário Mongo DB --
- MONGO_PASSWORD: -- Senha Mongo DB --
- MONGO_AUTH: -- Forma de autenticação do Mongo DB --
- JAVA_OPTS: -- Opções para JVM -- (Limitar Memória)

Executar os passo em ferramenta de deploy(pipelines, jenkins, etc):

- Construir imagem docker da aplicação - trigger: commit na master
- Publicar imagem docker em repositório de imagens
- Utilizar kubectl para publicar serviço a partir da imagem no cluster K8S
