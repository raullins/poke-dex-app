Pokedex App

Aplicativo Android desenvolvido em Kotlin que utiliza a arquitetura MVVM, Jetpack Compose, Room, Retrofit e Hilt para consumir dados da API PokeApi. O aplicativo permite aos usuários visualizar uma lista de Pokémon, pesquisar por nome, favoritar seus Pokémon preferidos e acessar seus favoritos offline.

Funcionalidades
Lista de Pokémon: Exibe uma lista paginada de Pokémon, obtendo dados da PokeApi.
Pesquisa de Pokémon: Permite a busca de Pokémon pelo nome.
Favoritar Pokémon: Permite adicionar Pokémon aos favoritos e visualizá-los offline.
Visualização de Detalhes: Exibe detalhes sobre cada Pokémon, incluindo imagens e estatísticas.
Offline: Salva os Pokémon favoritos localmente para acesso offline.
Tecnologias Utilizadas
Kotlin: Linguagem principal do projeto.
Jetpack Compose: Toolkit moderno de UI para criar interfaces nativas.
MVVM: Padrão de arquitetura utilizado no desenvolvimento.
Room: Biblioteca de persistência para salvar dados localmente.
Retrofit: Biblioteca para consumo de APIs REST.
Hilt: Biblioteca para injeção de dependências.
Coroutines e Flow: Para operações assíncronas e manipulação de dados em tempo real.
Configuração e Instalação
Clone o repositório:

bash
Copiar código
git clone https://github.com/seu-usuario/pokedex.git
cd pokedex
Abra o projeto no Android Studio:

Certifique-se de ter o Android Studio instalado.
Abra o projeto e aguarde o download das dependências.
Configuração da API:

O aplicativo consome dados da API pública PokeApi. Certifique-se de que a URL base da API (BASE_URL) está configurada corretamente no AppModule.

kotlin
Copiar código
const val BASE_URL = "https://pokeapi.co/api/v2/"
Construir e Executar:

Selecione um dispositivo ou emulador e clique em "Run".
Estrutura do Projeto
data/: Contém fontes de dados, incluindo repositórios, DAOs e entidades do Room.
remote/: Definições para consumir a API (Retrofit).
local/: Configurações de persistência de dados locais (Room).
ui/: Componentes de UI usando Jetpack Compose.
list/: Exibe a lista de Pokémon e a pesquisa.
detail/: Exibe os detalhes de um Pokémon.
favorites/: Tela de visualização de favoritos.
di/: Configurações de injeção de dependências usando Hilt.
viewmodel/: Contém as ViewModels para gerenciar o estado e a lógica de negócios.
Instruções de Uso
Lista de Pokémon: Ao abrir o aplicativo, você verá uma lista de Pokémon obtida da API. Role para carregar mais.
Pesquisar Pokémon: Use a barra de pesquisa para filtrar Pokémon por nome.
Favoritar Pokémon: Clique no ícone de estrela na tela de detalhes para favoritar um Pokémon.
Visualizar Favoritos: Acesse a tela de favoritos para visualizar seus Pokémon salvos offline.
Contribuindo
Faça um Fork do projeto.
Crie sua feature branch: git checkout -b minha-feature.
Commit suas mudanças: git commit -m 'Adicionei uma nova feature'.
Faça o Push para a branch: git push origin minha-feature.
Abra um Pull Request.
