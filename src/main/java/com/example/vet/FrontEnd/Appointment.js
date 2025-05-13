  const apiUrl = "http://localhost:8080/api/consultas";
    const container = document.getElementById("consultas-container");
    const modal = document.getElementById("modalForm");
    const form = document.getElementById("consultaForm");
    const addBtn = document.getElementById("addConsultaBtn");
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


function formatarDataBrasileira(isoString) {
  const data = new Date(isoString);
  const dia = String(data.getDate()).padStart(2, '0');
  const mes = String(data.getMonth() + 1).padStart(2, '0');
  const ano = data.getFullYear();
  const hora = String(data.getHours()).padStart(2, '0');
  const min = String(data.getMinutes()).padStart(2, '0');

  return `${dia}/${mes}/${ano} às ${hora}:${min}`;
}






    let isEditing = false;

   async function carregarConsultas() {
     try {
       const usuario = JSON.parse(localStorage.getItem("usuario"));
       const tipo = usuario?.tipo;
       let animais = [];

       if (tipo === "TUTOR") {
         const animaisRes = await fetch(`http://localhost:8080/api/animais/by-tutor-email?email=${encodeURIComponent(usuario.email)}`);
         animais = await animaisRes.json();
       } else {
         const animaisRes = await fetch("http://localhost:8080/api/animais");
         animais = await animaisRes.json();
       }

       const animalIdToName = new Map();
       const tutorAnimalIds = new Set();
       animais.forEach(animal => {
         animalIdToName.set(animal.id, animal.name);
         tutorAnimalIds.add(animal.id);
       });

       const res = await fetch(apiUrl);
       const lista = await res.json();

       container.innerHTML = "";

       lista.forEach(c => {
         if (tipo === "TUTOR" && !tutorAnimalIds.has(c.id_animal)) return;

         const animalNome = animalIdToName.get(c.id_animal) || `ID ${c.id_animal}`;

         const card = document.createElement("div");
         card.className = "consulta-card";
         card.innerHTML = `
           <h3>Animal: ${animalNome}</h3>
           <p><strong>Data:</strong> ${formatarDataBrasileira(c.date_time)}</p>
           <p><strong>Veterinário:</strong> ${c.vet_name}</p>
           <p><strong>Queixa:</strong> ${c.tutorComplaint}</p>
           <p><strong>Diagnóstico:</strong> ${c.diagnosis}</p>
           <button class="edit" onclick="editar(${c.id}, ${c.id_animal}, '${c.tutorComplaint}', '${c.date_time}', '${c.vet_name}', '${c.diagnosis}')">Editar</button>
           <button class="delete" onclick="deletar(${c.id})">Excluir</button>
         `;
         container.appendChild(card);
       });
     } catch (err) {
       container.innerHTML = "<p>Erro ao carregar consultas.</p>";
       console.error(err);
     }
   }



  function editar(id, id_animal, tutorComplaint, date_time, vet_name, diagnosis) {
    isEditing = true;
    formTitle.textContent = "Editar Consulta";
    modal.classList.remove("hidden");
    form.id.value = id;
    form.id_animal.value = id_animal;
    form.tutorComplaint.value = tutorComplaint;
    form.date_time.value = date_time;
    form.vet_name.value = vet_name;
    form.diagnosis.value = diagnosis;

    const tipo = JSON.parse(localStorage.getItem("usuario"))?.tipo;
    form.diagnosis.readOnly = (tipo === "TUTOR");
  }

    function deletar(id) {
      if (confirm("Deseja excluir esta consulta?")) {
        fetch(`${apiUrl}/${id}`, { method: "DELETE" })
          .then(() => carregarConsultas());
      }
    }

    form.addEventListener("submit", (e) => {
      e.preventDefault();
      const data = {
        id_animal: form.id_animal.value,
        tutorComplaint: form.tutorComplaint.value,
        date_time: form.date_time.value,
        vet_name: form.vet_name.value,
        diagnosis: form.diagnosis.value
      };

      if (isEditing) {
        data.id = form.id.value;
      }

      fetch(apiUrl, {
        method: isEditing ? "PUT" : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
      })
      .then(() => {
        form.reset();
        modal.classList.add("hidden");
        isEditing = false;
        carregarConsultas();
      });
    });

   addBtn.addEventListener("click", () => {
     form.reset();
     formTitle.textContent = "Nova Consulta";
     isEditing = false;
     modal.classList.remove("hidden");

     const tipo = JSON.parse(localStorage.getItem("usuario"))?.tipo;
     const diagnosisField = document.getElementById("diagnosis");
     const diagnosisGroup = document.getElementById("diagnosticoGroup");

     if (tipo === "TUTOR") {
       diagnosisField.value = "";
       diagnosisField.readOnly = true;
       diagnosisGroup.classList.add("hidden"); // ou só deixar readonly se quiser manter visível
     } else {
       diagnosisField.readOnly = false;
       diagnosisGroup.classList.remove("hidden");
     }
   });


    closeBtn.addEventListener("click", () => {
      modal.classList.add("hidden");
      isEditing = false;
    });

    carregarConsultas();