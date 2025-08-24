# 📝 PreListaApp

PreListaApp é um aplicativo Android desenvolvido em **Kotlin** com **Jetpack Compose**, projetado para facilitar a criação de pré-listas de itens com quantidades. Ele permite:

- ✅ Adicionar itens
- 🔁 Ajustar quantidades
- 📦 Gerar uma lista final formatada
- 🔃 Reordenar os itens por arraste
- 🗑️ Excluir itens individualmente

---

## 📸 Capturas de Tela
<img width="322" height="767" alt="Image" src="https://github.com/user-attachments/assets/784b4b8d-0a26-4e4a-9c61-067d9fc825f4" />
<img width="312" height="770" alt="Image" src="https://github.com/user-attachments/assets/f973a078-425b-4824-a63d-3455643c15b3" />
<img width="319" height="756" alt="Image" src="https://github.com/user-attachments/assets/d48ef419-fdd2-4241-94b2-8ad0847dfa78" />



---

## 🚀 Tecnologias Utilizadas

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room)
- [Material 3 (Material You)](https://m3.material.io/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Android Studio](https://developer.android.com/studio)
- Biblioteca de ordenação: [`compose-reorderable`](https://github.com/aclassen/compose-reorderable)

---

## 🛠️ Como executar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/PreListaApp.git
   ```

2. Abra o projeto no **Android Studio**

3. Conecte um dispositivo físico ou emulador

4. Clique em ▶️ **Run**

---

## 📂 Estrutura do Projeto

```
PreListaApp/
├── components/            # Diálogos e componentes reutilizáveis
├── screens/               # Telas principais (ex: TelaItensDaLista)
├── AppDatabase.kt         # Configuração do Room
├── Item.kt                # Entidade de Item
└── MainActivity.kt        # Entry point
```

---

## ✨ Funcionalidades

- 🔍 Exibição dos itens da lista
- ➕ Inclusão de novos itens com ordem controlada
- ➖➕ Ajuste de quantidades
- 📥 Geração de texto com os itens selecionados
- 📤 Copiar para área de transferência
- 🎯 Reordenação com `drag and drop`
- 💬 Diálogos de confirmação para excluir ou adicionar

---

## 👨‍💻 Autor

**Eric Matheus**  
📍 Brasília, DF  
🎓 Formado em Análise e Desenvolvimento de Sistemas  
💻 Atualmente cursando Ciências da Computação  
📬 Contato: _adicione aqui seu e-mail ou LinkedIn se quiser_

---

## 📝 Licença

Este projeto está sob a licença **MIT**.  
Sinta-se livre para usar, estudar e adaptar!
