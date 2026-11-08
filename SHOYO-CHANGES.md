# CYBERNETIC LAUNCHER (base ZL2) — alterações feitas

Base: ZalithLauncher2 v2.8.2 (GPLv3, © MovTery e colaboradores). Mantenha LICENSE e os avisos de copyright.

## Etapa 1
- Tema "Vermelho Shoyo" (preto + vermelho), padrão e sempre escuro (ColorThemeType.SHOYO_RED).
- Menu lateral em pílulas (ShoyoSidebar.kt): Início, Versões, Download, Contas, Multiplayer, Ajustes.
  Esconde ao escolher uma seção; botão de menu na barra superior reabre. Sem ripple.
- Só renderizadores OpenGL: NG-GL4ES, GL4ES, MobileGlues (Renderers.kt).
- Nome CYBERNETIC LAUNCHER, versão 1.0.1, applicationId com.shoyoo.mc.shoyo.launch.
- Ícone oficial (emblema dourado), logo no Sobre/splash, README próprio; removidos do app: atualização, patrocinadores, links e créditos de autor do ZL2.

## Ainda não feito
- Opção de driver "Vortex (teste)" (depende de qual driver/biblioteca é).
- Barra superior no estilo Shoyo, telas Início/Versões/Contas/Download/Ajustes redesenhadas.
- Vídeo de fundo já existe no ZL2; faltam limite de 32 s e taxa de atualização máxima.
- Macro (auto-clicker), ícone ♦, créditos no Sobre, remover nomes/links do ZL2 da interface.
- Não foi compilado (sem Android SDK aqui): o 1º build pode mostrar erros pequenos.

## Como compilar
GitHub: envie a pasta, aba Actions > "Build CI" (Run workflow). Baixe o APK em Artifacts.
Precisa de JDK 21. Chaves OAuth/CurseForge são opcionais (secrets OAUTH_CLIENT_ID, CURSEFORGE_API_KEY).
