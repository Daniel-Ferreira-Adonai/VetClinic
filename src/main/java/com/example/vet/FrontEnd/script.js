const apiUrl = "http://localhost:8080/api/tutores";
const container = document.getElementById("tutores-container");
const modal = document.getElementById("modalForm");
const form = document.getElementById("tutorForm");
const addBtn = document.getElementById("addTutorBtn");
const closeBtn = document.getElementById("closeModal");
const formTitle = document.getElementById("form-title");



(function configurarLoginLogout() {
  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const loginLogoutLi = document.querySelector(".login-logout");

  if (!loginLogoutLi) return;

  const menuItems = document.querySelectorAll(".admin-navbar ul li");



  if (!usuario) {
    menuItems.forEach(li => {
      const link = li.querySelector("a");
      if (
        link &&
        !link.href.includes("index.html") &&
        !link.href.includes("login.html") &&
        !li.classList.contains("login-logout")
      ) {
        li.remove();
      }
    });

    loginLogoutLi.innerHTML = `<a href="login.html">Login</a>`;
    return;
  }


  const tipo = usuario.tipo;

  const permitidoParaVetETutor = [
    "index.html",
    "AnimalForm.html",
    "tutorForm.html",
    "Appointment.html"
  ];

  menuItems.forEach(li => {
    const link = li.querySelector("a");
    if (!link) return;

    const href = link.getAttribute("href");

    if (tipo !== "ADMIN" && !permitidoParaVetETutor.includes(href)) {
      if (!li.classList.contains("login-logout")) {
        li.remove();
      }
    }
  });

  loginLogoutLi.innerHTML = `
    <div style="text-align: right;">
      <button id="logoutBtn" class="logout-btn">Sair</button>
    </div>
  `;

  setTimeout(() => {
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
      logoutBtn.addEventListener("click", () => {
        localStorage.clear();
        window.location.href = "login.html";
      });
    }
  }, 0);
})();

const tipo = JSON.parse(localStorage.getItem("usuario"))?.tipo;

if (tipo === "TUTOR") {
  addBtn.style.display = "none";
}




let isEditing = false;

function carregarTutores() {
  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const tipo = usuario?.tipo;

  container.innerHTML = "";

  if (tipo === "TUTOR") {
    fetch(`${apiUrl}/email/${encodeURIComponent(usuario.email)}`)
      .then(res => res.json())
      .then(tutor => {
        const card = document.createElement("div");
        card.className = "tutor-card";
        card.innerHTML = `
          <h3>${tutor.name}</h3>
          <p>Email: ${tutor.email}</p>
          <p>Telefone: ${tutor.phone}</p>
          <button class="edit" onclick="editarTutor(${tutor.id}, '${tutor.name}', '${tutor.phone}', '${tutor.email}')">Editar</button>

        `;
        container.appendChild(card);
      })
      .catch(() => {
        container.innerHTML = `<p>Erro ao carregar tutor.</p>`;
      });
  } else {
    // ADMIN e VET
    fetch(apiUrl)
      .then(res => res.json())
      .then(tutores => {
        tutores.forEach(tutor => {
          const card = document.createElement("div");
          card.className = "tutor-card";
          card.innerHTML = `
            <h3>${tutor.name}</h3>
            <p>Email: ${tutor.email}</p>
            <p>Telefone: ${tutor.phone}</p>
            <button class="edit" onclick="editarTutor(${tutor.id}, '${tutor.name}', '${tutor.phone}', '${tutor.email}')">Editar</button>
            <button class="delete" onclick="deletarTutor(${tutor.id})">Excluir</button>
          `;
          container.appendChild(card);
        });
      });
  }
}

window.editarTutor = (id, name, phone, email) => {
  isEditing = true;
  formTitle.textContent = "Editar Tutor";
  modal.classList.remove("hidden");
  document.getElementById("id_tutor").value = id;
  form.name.value = name;
  form.phone.value = phone;
  form.email.value = email;
};

window.deletarTutor = (id) => {
  if (confirm("Deseja realmente excluir este tutor?")) {
    fetch(`${apiUrl}/${id}`, {
      method: "DELETE"
    }).then(() => carregarTutores());
  }
};

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const data = {
    name: form.name.value,
    phone: form.phone.value,
    email: form.email.value
  };

  let method = "POST";
  let url = apiUrl;

  if (isEditing) {
    data.id_tutor = String(document.getElementById("id_tutor").value); // ID como string
    method = "PUT";
  }

  fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)
  })
    .then(async (res) => {
      if (!res.ok) {
        const msg = await res.text();
        alert("Erro ao salvar: " + msg);
      } else {
        form.reset();
        modal.classList.add("hidden");
        isEditing = false;
        carregarTutores();
      }
    })
    .catch(err => alert("Erro de conexão: " + err.message));
});

addBtn.addEventListener("click", () => {
  form.reset();
  formTitle.textContent = "Adicionar Tutor";
  isEditing = false;
  modal.classList.remove("hidden");
});

closeBtn.addEventListener("click", () => {
  modal.classList.add("hidden");
  isEditing = false;
});

carregarTutores();
