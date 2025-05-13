const apiUrl = "http://localhost:8080/api/servicos";
const container = document.getElementById("servicos-container");
const modal = document.getElementById("modalForm");
const form = document.getElementById("servicoForm");
const addBtn = document.getElementById("addServiceBtn");
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





let isEditing = false;

function carregarServicos() {
  fetch(apiUrl)
    .then(res => res.json())
    .then(servicos => {
      container.innerHTML = "";
      servicos.forEach(s => {
        const card = document.createElement("div");
        card.className = "servico-card";
        card.innerHTML = `
          <img src="${s.url}" alt="${s.name}" />
          <div class="info">
            <h3>${s.name}</h3>
            <p><strong>Tipo:</strong> ${s.serviceType}</p>
            <p><strong>Preço:</strong> R$ ${s.price.toFixed(2)}</p>
            <p>${s.description}</p>
            <div class="actions">
              <button class="edit" onclick="editarServico(${s.id}, '${s.name}', '${s.description}', ${s.price}, '${s.url}', '${s.serviceType}')">Editar</button>
              <button class="delete" onclick="deletarServico(${s.id})">Excluir</button>
            </div>
          </div>
        `;
        container.appendChild(card);
      });
    });
}

window.editarServico = (id, name, description, price, url, type) => {
  isEditing = true;
  formTitle.textContent = "Editar Serviço";
  modal.classList.remove("hidden");
  form.id.value = id;
  form.name.value = name;
  form.description.value = description;
  form.price.value = price;
  form.url.value = url;
  form.type.value = type;
};

window.deletarServico = (id) => {
  if (confirm("Deseja realmente excluir este serviço?")) {
    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
      .then(() => carregarServicos());
  }
};

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const data = {
    name: form.name.value,
    description: form.description.value,
    price: parseFloat(form.price.value),
    url: form.url.value,
    type: form.type.value
  };

  let method = "POST";
  let urlEndpoint = apiUrl;

  if (isEditing) {
    data.id = form.id.value;
    method = "PUT";
  }

  fetch(urlEndpoint, {
    method: method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  }).then(() => {
    form.reset();
    modal.classList.add("hidden");
    isEditing = false;
    carregarServicos();
  });
});

addBtn.addEventListener("click", () => {
  form.reset();
  formTitle.textContent = "Adicionar Serviço";
  isEditing = false;
  modal.classList.remove("hidden");
});

closeBtn.addEventListener("click", () => {
  modal.classList.add("hidden");
  isEditing = false;
});

carregarServicos();
