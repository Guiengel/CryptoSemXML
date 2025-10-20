# Checkpoint2Kotlin


## Implementação do código:

Implementação das classes/layouts:

![Classes&Layouts](./img/Imagem1.png)

Um pouco do código do MainActivity:

![MainActivity](./img/Imagem2.png)

Código do layout activity_main:

![activity_main](./img/Imagem3.png)

---

## Executando o código:

![Execução](./img/Imagem4.png)

Uma outra print mas com zoom:

![ExecuçãoZoom](./img/Imagem5.png)

Depois de apertar no atualizar para fazer a chamada API:

![Executado](./img/Imagem6.png)

---

## Explicando os arquivos kotlin:

### Service: 

A classe service representa uma regra de negócio ou a lógica principal da aplicação, executa tarefas em segundo plano e pode interagir com outros componentes da aplicação.

Como por exemplo, nessa service do projeto:

![Service](./img/Imagem7.png)

Aqui, ela faz uma requisição HTTP do método GET no caminho da API (api/BTC/ticker), onde na classe factory, contém a URL

---

### Factory:

Contém métodos que são usados para criar e retornar instâncias de outras classes

![Factory](./img/Imagem8.png)

Aqui, ela define a URL padrão que será usada em todas as chamadas e já faz a conversão da API. E depois usa o retrofit para gerar a implementação da interface da service

---

### Main

A MainActivity.kt é a tela principal do app, ela está configurada para mostrar um titulo na toolbar, tem o botão de “atualizar“ para executar a chamada API e buscar o valor atual do Bitcoin, quando a resposta chega, ele atualiza nos campos de texto que necessita, e se der erro, mostra uma mensagem de erro

![MainActivity](./img/Imagem2.png)

---

### Model

A Model é criada para representar dados de forma simples e concisa

![Model](./img/Imagem9.png)

Nessa model, representamos dados que ela vai receber da API do Mercado Bitcoin, “Ticker“ sendo o objeto que contém todas as informações enquanto “TicketResponse“ estrutura a resposta principal da API








