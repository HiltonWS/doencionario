# Doencionario

Seja bem vindo ao projeto!

## Como iniciar?

Primeiro pode-se clonar esse projeto com o comando: 

```
git clone https://gitlab.com/HiltonWS/doencionario.git
```

Para iniciar a aplicação utilizando o maven, favor utilizar o seguinte comando:

```
mvn spring-boot:run
```

Pronto a aplicação estara rodando em http://localhost:8080/

## Aplicação iniciada! Como uso?

Para isso foi usado o Swagger com a OpenApi 3.0, assim acessando o seguinte endereço:

```
http://localhost:8080/swagger-ui.html
```
Nesse endereço pode-se ver o que cada serviço é, e o que cada serviço espera :D

Mas, para acessa-los deve-se usar um usuário e senha.

### Autenticando...

No serviço `login`, usar o seguinte `username` e `password`

```
{
  "username": "admin",
  "password": "admin"
}
```

Ele retornará no `header` um `bearer` token, copiar somente o token e ir em `Authorize`, colar logar e fechar a janela. Pronto! Agora esse token tem uma duração de 10 minutos para ser usado.

## Usando na nuvem (AWS)

~Foi deixado habilitado o Swagger na nuvem também através deste endereço http://ec2-18-209-94-100.compute-1.amazonaws.com/swagger-ui.html~


## Tecnologias ultilizadas
* Java 8
* SpringBoot
* Spring MVC
* Hibernate
* Banco em memória H2
* Maven
* Swagger-ui, com SpringDocs
* JWT
* Junit
