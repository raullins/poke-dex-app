<h1 align="center">Pokedex App - PT(BR)</h1>

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)


Aplicativo Android desenvolvido em Kotlin que utiliza a arquitetura `MVVM`, além das ferramentas `Jetpack Compose`, `Palette`, `Room`, `Hilt` e `Retrofit` para consumir dados da API PokeApi além de `Coil` e `Glide` para gerenciamento de recursos visuais. O aplicativo permite aos usuários visualizar uma lista de Pokémon, pesquisar por nome e abrir os detalhes dos Pokémon. As features de favoritar seus Pokémon preferidos e acessar seus favoritos offline ainda estão em desenvolvimento. As features de favoritar e acessar seus favoritos não estão mais em desenvolvimento, pois foram concluídas!

 <br>
 
* [Funcionalidades](#funcionalidades)

**Lista de Pokémon**: Exibe uma lista paginada de Pokémon, obtendo dados da PokeApi;

**Pesquisa de Pokémon**: Permite a busca de Pokémon pelo nome;

__Favoritar Pokémon__: Permite adicionar Pokémon aos favoritos e visualizá-los offline;

__Visualização de Detalhes__: Exibe detalhes sobre cada Pokémon, incluindo imagens e estatísticas;

__Offline__: Salva os Pokémon favoritos localmente para acesso offline;

__Mudança de tema__: Tema da aplicação que acompanha o modo dark/light do sistema;

 <br>
 
* [Tecnologias utilizadas](#tecnologias-utilizadas)
  
`Kotlin`: Linguagem principal do projeto;

`Jetpack Compose`: Toolkit moderno de UI para criar interfaces nativas;

`Palette`: Biblioteca utilizada para extrair as cores principais de uma imagem;

`MVVM`: Padrão de arquitetura utilizado no desenvolvimento;

`Room`: Biblioteca de persistência para salvar dados localmente;

`Retrofit`: Biblioteca para consumo de APIs REST;

`Hilt`: Biblioteca para injeção de dependências;

`Coroutines e Flow`: Para operações assíncronas e manipulação de dados em tempo real;

`Coil e Glide`: Bibliotecas com a função de gerenciamento de recursos visuais;


 <br>
 
* [Configuração e Instalação](#configuração-e-instalação)

1. Clone o repositório:
```
git clone https://github.com/seu-usuario/pokedex.git

cd pokedex
```
2. Abra o projeto no Android Studio:

\- Certifique-se de ter o Android Studio instalado.

\- Abra o projeto e aguarde o download das dependências.

3. Configuração da API:

\- O aplicativo consome dados da API pública PokeApi. Certifique-se de que a URL base da API (BASE_URL) está configurada corretamente no AppModule.
```
const val BASE_URL = "https://pokeapi.co/api/v2/"
```
4. Construir e Executar:

\- Selecione um dispositivo ou emulador e clique em "Run".


 <br>
 
* [Estrutura do Projeto](#estrutura-do-projeto)
 
`data/`: Contém fontes de dados, incluindo repositórios, DAOs e entidades do Room;

`remote/`: Definições para consumir a API (Retrofit);

`local/`: Configurações de persistência de dados locais (Room);

`ui/`: Componentes de UI usando Jetpack Compose;

`list/`: Exibe a lista de Pokémon e a pesquisa;

`detail/`: Exibe os detalhes de um Pokémon;

`favorites/`: Tela de visualização de favoritos;

`di/`: Configurações de injeção de dependências usando Hilt;

`viewmodel/`: Contém as ViewModels para gerenciar o estado e a lógica de negócios.

<br>

* [Instruções de Uso](#instruções-de-uso)
  

**Lista de Pokémon**: Ao abrir o aplicativo, você verá uma lista de Pokémon obtida da API. Role para carregar mais;

**Pesquisar Pokémon**: Use a barra de pesquisa para filtrar Pokémon por nome;

**Ver detalhes do Pokémon**: Clique na box referente ao Pokémon para vizualizar mais detalhes sobre ele;

**Favoritar Pokémon**: Clique no ícone de coração na tela de listagem para favoritar um Pokémon;

**Visualizar Favoritos**: Acesse a tela de favoritos para visualizar seus Pokémon salvos offline;

**Mudar Tema**: A aplicação acompanha o tema principal do dispositivo. Basta alterar o tema do aparelho para tambem mudar o tema da aplicação.

<br>

* [Contribuindo](#contribuindo)

\- Faça um Fork do projeto;

\- Crie sua feature branch: 
```
git checkout -b minha-feature;
```
\- Commit suas mudanças: 
```
git commit -m 'Adicionei uma nova feature'.
```
\- Faça o Push para a branch:
```
git push origin minha-feature;
```
\- Abra um Pull Request.

<br>
 
* [Exemplos de uso](#exemplos-de-uso)

![Listagem](https://github.com/user-attachments/assets/d81a9375-ebaa-4c1b-ac2c-7b551ed25ba0)


Listagem

<br>

![Ver detalhes](https://github.com/user-attachments/assets/2558b8cd-985d-4584-92af-82565d4786b9)


Ver detalhes

<br>

![Favoritos](https://github.com/user-attachments/assets/83ca3735-9818-461a-aa68-475d0f32f4e0)


Favoritar e lista de favoritos

<br>

![Procurar pokemon](https://github.com/user-attachments/assets/fd94cc8a-7e87-4bf2-bf68-ef8bcc5ad478)


Procurar pokemon

<br>

![Light y Dark mode](https://github.com/user-attachments/assets/5d58aab6-9932-47b1-bb6f-4043a5419220)


Light/dark mode

<br>

<h1 align="center">Pokedex App - EN(US)</h1>

Android app developed in Kotlin using the `MVVM` architecture, along with tools like `Jetpack Compose`, `Palette`, `Room`, `Hilt`, and `Retrofit` to consume data from the PokeApi. It also uses `Coil` and `Glide` for handling visual resources. The app allows users to view a list of Pokémon, search by name, and open Pokémon details. The features to favorite Pokémon and access them offline are still in development. However, the features for favoriting and accessing favorites have been completed!

<br>

* [Features](#features)

**Pokémon List:** Displays a paginated list of Pokémon, fetching data from the PokeApi;

**Pokémon Search:** Allows searching for Pokémon by name;

**Favorite Pokémon:** Lets users add Pokémon to favorites and view them offline;

**Detail View:** Displays details of each Pokémon, including images and stats;

**Offline:** Saves favorite Pokémon locally for offline access;

**Theme Change:** The app follows the system’s dark/light theme settings;

<br>

* [Technologies Used](#tecnologies-used)

`Kotlin`: The main language used in the project;

`Jetpack Compose`: A modern UI toolkit to build native interfaces;

`Palette`: A library used to extract the main colors from an image;

`MVVM`: The architectural pattern used in development;

`Room`: A persistence library for saving data locally;

`Retrofit`: A library to consume REST APIs;

`Hilt`: A library for dependency injection;

`Coroutines e Flow`: For asynchronous operations and real-time data handling;

`Coil e Glide`: Libraries used to handle visual resources;

 <br>
 
* [Setup and Installation](#setup-and-installation)

1. Clone the repository:
```
git clone https://github.com/your-username/pokedex.git

cd pokedex
```
2. Open the project in Android Studio:

\- Ensure you have Android Studio installed.

\- Open the project and wait for the dependencies to download.

3. API Configuration:

\- The app consumes data from the public PokeApi. Make sure the base API URL (BASE_URL) is properly configured in the AppModule.
```
const val BASE_URL = "https://pokeapi.co/api/v2/"
```
4. Build and Run:
   
\- Select a device or emulator and click "Run".

<br>

* [Project Structure](#project-structure)
 
`data/`: Contains data sources, including repositories, DAOs, and Room entities;

`remote/`: Definitions for consuming the API (Retrofit);

`local/`: Local data persistence setup (Room);

`ui/`: UI components using Jetpack Compose;

`list/`: Displays the Pokémon list and search functionality;

`detail/`: Displays Pokémon details;

`favorites/`: Favorites view screen;

`di/`: Dependency injection setup using Hilt;

`viewmodel/`: Contains ViewModels for managing state and business logic.

<br>

* [Usage Instructions](#usage-instructions)
  
**Pokémon List**: When opening the app, you will see a Pokémon list fetched from the API. Scroll to load more;

**Search Pokémon**: Use the search bar to filter Pokémon by name;

**View Pokémon Details**: Click on a Pokémon box to view more details;

**Favorite Pokémon**: Click the heart icon on the list screen to favorite a Pokémon;

**View Favorites**: Access the favorites screen to view your saved Pokémon offline;

**Change Theme**: The app follows the device’s main theme. Simply change your device's theme, and the app will switch accordingly.

<br>

* [Contributing](#contributing)

\- Fork the project;

\- Create your feature branch:
```
git checkout -b my-feature;
```
\- Commit your changes:
```
git commit -m 'Added a new feature'.
```
\- Push to the branch:
```
git push origin my-feature;
```
\- Open a Pull Request.
