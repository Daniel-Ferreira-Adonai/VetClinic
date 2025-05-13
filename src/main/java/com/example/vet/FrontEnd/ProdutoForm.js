const apiUrl = "http://localhost:8080/api/produtos";
const container = document.getElementById("produtos-container");
const modal = document.getElementById("modalForm");
const form = document.getElementById("produtoForm");
const addBtn = document.getElementById("addProductBtn");
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

function carregarProdutos() {
  fetch(apiUrl)
    .then(res => res.json())
    .then(produtos => {
      container.innerHTML = "";
      produtos.forEach(p => {
        const card = document.createElement("div");
        card.className = "produto-card";
        card.innerHTML = `
          <img src="${p.url}" alt="${p.name}" />
          <div class="info">
            <h3>${p.name}</h3>
            <p><strong>Preço:</strong> R$ ${p.price.toFixed(2)}</p>
            <p><strong>Estoque:</strong> ${p.quantity}</p>
            <p>${p.description}</p>
            <div class="actions">
              <button class="edit" onclick="editarProduto(${p.id}, '${p.name}', '${p.description}', ${p.price}, ${p.quantity}, '${p.url}')">Editar</button>
              <button class="delete" onclick="deletarProduto(${p.id})">Excluir</button>
            </div>
          </div>
        `;
        container.appendChild(card);
      });
    });
}

window.editarProduto = (id, name, description, price, quantity, url) => {
  isEditing = true;
  formTitle.textContent = "Editar Produto";
  modal.classList.remove("hidden");
  form.id.value = id;
  form.name.value = name;
  form.description.value = description;
  form.price.value = price;
  form.quantity.value = quantity;
  form.url.value = url;
};

window.deletarProduto = (id) => {
  if (confirm("Deseja realmente excluir este produto?")) {
    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
      .then(() => carregarProdutos());
  }
};

form.addEventListener("submit", (e) => {
  e.preventDefault();

  const data = {
    name: form.name.value,
    description: form.description.value,
    price: parseFloat(form.price.value),
    quantity: parseInt(form.quantity.value),
    url: form.url.value
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
    carregarProdutos();
  });
});

addBtn.addEventListener("click", () => {
  form.reset();
  formTitle.textContent = "Adicionar Produto";
  isEditing = false;
  modal.classList.remove("hidden");
});

closeBtn.addEventListener("click", () => {
  modal.classList.add("hidden");
  isEditing = false;
});

carregarProdutos();
