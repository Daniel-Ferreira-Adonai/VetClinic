const apiUrl = "http://localhost:8080/api/animais";
const container = document.getElementById("animais-container");
const modal = document.getElementById("modalForm");
const form = document.getElementById("animalForm");
const addBtn = document.getElementById("addAnimalBtn");
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

document.addEventListener("DOMContentLoaded", () => {
  const usuario = JSON.parse(localStorage.getItem("usuario"));
  if (usuario?.tipo === "TUTOR") {
    const emailGroup = document.getElementById("emailTutorGroup");
    const emailInput = document.getElementById("emailTutor");
    if (emailGroup && emailInput) {
      emailInput.removeAttribute("required");
      emailGroup.classList.add("hidden");
    }
  }
});







let isEditing = false;

function carregarAnimais() {
    const usuario = JSON.parse(localStorage.getItem("usuario"));
    const tipo = usuario?.tipo;

    const url = (tipo === "TUTOR")
        ? `${apiUrl}/by-tutor-email?email=${encodeURIComponent(usuario.email)}`
        : apiUrl;

    fetch(url)
        .then(res => res.json())
        .then(animais => {
            container.innerHTML = "";
            animais.forEach(animal => {
                const card = document.createElement("div");
                card.className = "animal-card";
                card.innerHTML = `
                    <h3>${animal.name}</h3>
                    <p>Espécie: ${animal.especie}</p>
                    <p>Raça: ${animal.breedType}</p>
                    <button class="edit" onclick="editarAnimal(${animal.id}, '${animal.name}', '${animal.especie}', '${animal.breedType}', ${animal.tutor_id})">Editar</button>
                    <button class="delete" onclick="deletarAnimal(${animal.id})">Excluir</button>
                `;
                container.appendChild(card);
            });
        });
}



window.editarAnimal = (id, nome, especie, raca, tutor_id) => {
  isEditing = true;
  formTitle.textContent = "Editar Animal";
  modal.classList.remove("hidden");
  form.id_animal.value = id;
  form.nome.value = nome;
  form.especie.value = especie;
  form.raca.value = raca;

  form.setAttribute("data-tutor-id", tutor_id);

  const emailGroup = document.getElementById("emailTutorGroup");
  const emailInput = document.getElementById("emailTutor");
  if (emailGroup) emailGroup.classList.add("hidden");
  if (emailInput) emailInput.removeAttribute("required");
};



window.deletarAnimal = (id) => {
    if (confirm("Deseja realmente excluir este animal?")) {
        fetch(`${apiUrl}/${id}`, {
            method: "DELETE"
        }).then(() => carregarAnimais());
    }
};
if (JSON.parse(localStorage.getItem("usuario"))?.tipo === "TUTOR") {
    document.getElementById("emailTutorGroup")?.classList.add("hidden");
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const tipo = usuario?.tipo;

  const data = {
    name: form.nome.value,
    especie: form.especie.value,
    breedType: form.raca.value
  };

  let method = "POST";

  if (isEditing) {
    data.id_animal = form.id_animal.value;
    data.id_tutor = parseInt(form.dataset.tutorId);
    method = "PUT";
  } else {
    if (tipo === "TUTOR") {
      const tutor = JSON.parse(localStorage.getItem("tutor"));
      if (!tutor?.email) {
        alert("Erro: email do tutor não encontrado.");
        return;
      }
      data.emailTutor = tutor.email;
    } else {
      const emailInput = document.getElementById("emailTutor");
      if (!emailInput?.value) {
        alert("Por favor, preencha o email do tutor.");
        return;
      }
      data.emailTutor = emailInput.value.trim();
    }
  }

  try {
    await fetch(apiUrl, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    form.reset();
    modal.classList.add("hidden");
    isEditing = false;
    carregarAnimais();
  } catch (err) {
    alert("Erro ao enviar dados: " + err.message);
  }
});




addBtn.addEventListener("click", () => {
  form.reset();
  formTitle.textContent = "Adicionar Animal";
  isEditing = false;
  modal.classList.remove("hidden");

  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const tipo = usuario?.tipo;

  const emailGroup = document.getElementById("emailTutorGroup");
  const emailInput = document.getElementById("emailTutor");

  if (tipo === "TUTOR") {
    if (emailGroup) emailGroup.classList.add("hidden");
    if (emailInput) emailInput.removeAttribute("required");
  } else {
    if (emailGroup) emailGroup.classList.remove("hidden");
    if (emailInput) emailInput.setAttribute("required", "required");
  }

  delete form.dataset.tutorId;
});

closeBtn.addEventListener("click", () => {
    modal.classList.add("hidden");
    isEditing = false;
});

carregarAnimais();
