#  Desafio Android do Mesa 

## Como executar o aplicativo?

Para executar o aplicativo, baixe o arquivo apk no link
[clique aqui para baixar o aplicativo ](https://github.com/patriciojdutra/News_desafio/blob/main/app-desafio-mesa.apk?raw=true)
 e instale em seu smartphone.

## Requisitos do aplicativo

1) Tela de Login
   a) O usuário poderá entrar com:
      - Email e Password;
      - Facebook;

2) Tela de Cadastro
   a) O usuário poderá cadastrar:
      - Nome
      - E-mail
      - Senha
      - Confirmação de senha
      - Data de Nascimento (Opcional)

3) Tela de Feed
   a) O usuário irá visualizar no topo da tela, um carrossel das notícias em destaques;
   b) O usuário irá visualizar uma listagem corrida com as notícias, ordenadas pela data de publicação;
   c) O usuário poderá salvar como favorito a notícia desejada;
   d) Cada notícia deve conter:
   i) Imagem;
   ii) Título (Máximo de 2 linhas)
   iii) Descrição (Máximo de 2 linhas)
   iv) Botão para favoritar/desfavoritar
   e) O usuário só vai poder acessar a tela de Feed autenticado
   
4) Tela de Filtros
   a) O usuário poderá filtrar as notícias por:
   i) Data;
   ii) Favoritos;
   
5) Tela de Detalhe da notícia
   a) O usuário poderá visualizar a notícia por uma WebView.
      
[Clique aqui para saber mais sobre o desafio.](https://github.com/patriciojdutra/News_desafio/blob/main/Desafio_Mesa_Mobile.pdf)
      
      
## Resultado das telas

#### Carregando os dados iniciais e paginação.
![Gifs](https://github.com/patriciojdutra/DesafioJeitto/blob/master/mobizen_20210125_022457.gif)

#### Mantendo os dados mesmo com rotação de tela.
![Gifs](https://github.com/patriciojdutra/DesafioJeitto/blob/master/mobizen_20210125_022745.gif)

#### Navegando até a url da noticia.
![Gifs](https://github.com/patriciojdutra/DesafioJeitto/blob/master/mobizen_20210125_023200.gif)

## Tecnologias e Frameworks

* Kotlin
* Arquitetura MVVM
  * ViewModel
  * LiveData
* Retrofit
* RecyclerView
* Picasso
* Json
* Animação

# Perguntas que devem ser respondidas

**Quais foram os principais desafios durante o desenvolvimento?**
Como eu não tinha muita familiaridade com MVVM, optei por desenvolver o app em MVC, onde levei cerca de um dia para finalizar o aplicativo. O principal desafio foi implantar a arquitetura MVVM, pois tive que dar uma estudada na documentação antes de implantar no projeto.

**O que você escolheu como arquitetura/bibliotecas e por que?**
Por ser um projeto pequeno, a arquitetura não é algo que teria um impacto significativo no projeto, inicialmente implementei a arquitetura MVC, mas como havia bastante tempo até a data da entrega, resolvi implementar MVVM.
Para consumir os dados da api, optei por usar retrofit, pois é uma das mais poderosas e populares bibliotecas de HTTP Client para Android.

**O que falta desenvolver / como poderiamos melhorar o que você entregou, caso não tenha tido tempo suficiente para terminar o desafio?**
Todas as funcionalidades do aplicativo foram desenvolvidas, e o tempo foi suficiente! O que poderia ter para melhorar o código, seria testes unitarios.







