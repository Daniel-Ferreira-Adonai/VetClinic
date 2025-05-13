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


    document.querySelectorAll('.tab-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        const tab = btn.dataset.tab;
        document.querySelectorAll('.tab').forEach(s => s.classList.remove('active'));
        document.getElementById(tab).classList.add('active');
      });
    });
const usuario = JSON.parse(localStorage.getItem("usuario"));
if (usuario?.tipo === "TUTOR" ||usuario?.tipo === "VET" || usuario?.tipo === "ADMIN"   ) {
  const emailInput = document.getElementById("emailTutor");
  const emailGroup = document.getElementById("emailGroup");

  if (emailInput && emailGroup) {
    emailInput.value = usuario.email;
    emailInput.setAttribute("readonly", true);
    emailInput.removeAttribute("required");
    emailGroup.classList.add("hidden");
    carregarAnimaisPorEmail(usuario.email);
  }
}

 async function carregarServicos() {
   const container = document.getElementById("servicos-container");
   const res = await fetch("http://localhost:8080/api/servicos");
   const servicos = await res.json();
   container.innerHTML = "";

   servicos.forEach(s => {
     container.innerHTML += `
       <div class="card">
         <img src="${s.url}" alt="${s.name}" />
         <h3>${s.name}</h3>
         <p>${s.description}</p>
         <span>R$ ${s.price.toFixed(2)}</span>
         <button onclick='adicionarAoCarrinho({
           id: ${s.id},
           name: "${s.name}",
           price: ${s.price},
           type: "serviço"
         })'>Adicionar</button>
       </div>
     `;
   });
 }

document.getElementById("appointmentForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("emailTutor").value;
  const animalId = document.getElementById("animalSelect").value;
  const dateTime = document.querySelector("input[type='datetime-local']").value;
  const vetName = document.querySelector("input[placeholder='Veterinário desejado']").value;
  const complaint = document.querySelector("textarea").value;

  if (!email || !animalId || !dateTime || !complaint) {
    alert("Por favor, preencha todos os campos.");
    return;
  }

  const data = {
    id_animal: parseInt(animalId),
    date_time: dateTime,
    vet_name: vetName,
    tutorComplaint: complaint,
    diagnosis: ""
  };

  try {
    const res = await fetch("http://localhost:8080/api/consultas", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (res.ok) {
      alert("Consulta cadastrada com sucesso!");
      document.getElementById("appointmentForm").reset();
      document.getElementById("animalSelect").innerHTML = `<option>Selecione seu animal</option>`;
    } else {
      const errorText = await res.text();
      alert("Erro ao cadastrar consulta:\n" + errorText);
    }
  } catch (err) {
    alert("Erro de conexão com o servidor.");
  }
});

    // Carregar produtos
 async function carregarProdutos() {
   const container = document.getElementById("produtos-container");
   const res = await fetch("http://localhost:8080/api/produtos");
   const produtos = await res.json();
   container.innerHTML = "";

   produtos.forEach(p => {
     container.innerHTML += `
       <div class="card">
         <img src="${p.url}" alt="${p.name}" />
         <h3>${p.name}</h3>
         <p>${p.description}</p>
         <span>R$ ${p.price.toFixed(2)}</span>
         <button onclick='adicionarAoCarrinho({
           id: ${p.id},
           name: "${p.name}",
           price: ${p.price},
           type: "produto"
         })'>Adicionar</button>
       </div>
     `;
   });
 }


    async function carregarAnimaisPorEmail(email) {
      const select = document.getElementById("animalSelect");
      select.innerHTML = `<option>Carregando...</option>`;
      try {
        const res = await fetch(`http://localhost:8080/api/animais/by-tutor-email?email=${encodeURIComponent(email)}`);
        if (!res.ok) throw new Error("Não possui animais cadastrados");

        const animais = await res.json();
        if (animais.length === 0) {
          select.innerHTML = `<option>Nenhum animal encontrado</option>`;
          return;
        }

        select.innerHTML = `<option disabled selected>Selecione seu animal</option>`;
        animais.forEach(animal => {
          select.innerHTML += `<option value="${animal.id}">${animal.name}</option>`;
        });
      } catch (err) {
        select.innerHTML = `<option>Não possui animais cadastrados</option>`;
      }
    }

    document.getElementById("emailTutor").addEventListener("blur", (e) => {
      const email = e.target.value;
      if (email && email.includes("@")) {
        carregarAnimaisPorEmail(email);
      }
    });
let carrinho = [];

function adicionarAoCarrinho(item) {
  const existente = carrinho.find(i => i.id === item.id && i.type === item.type);
  if (existente) {
    existente.quantity++;
  } else {
    carrinho.push({ ...item, quantity: 1 }); //
  }
  atualizarCarrinho();
}


function atualizarCarrinho() {
  const ul = document.getElementById("itens-carrinho");
  const totalSpan = document.getElementById("total-carrinho");
  ul.innerHTML = "";
  let total = 0;

  carrinho.forEach(item => {
    const li = document.createElement("li");
    li.classList.add("carrinho-item");
    li.innerHTML = `
      <div class="item-info">
        <strong>${item.name}</strong>
        <span class="item-tipo">${item.type}</span>
        <span class="item-preco">R$ ${item.price.toFixed(2)}</span>
        <span class="item-qtd">Qtd: ${item.quantity}</span>
      </div>
    `;
    ul.appendChild(li);
    total += item.price * item.quantity;
  });

  totalSpan.innerText = total.toFixed(2);
  document.getElementById("carrinho-sidebar").classList.add("active");
}

const sidebar = document.getElementById("carrinho-sidebar");

document.addEventListener("mousemove", (e) => {
  const windowWidth = window.innerWidth;
  const distanceFromRight = windowWidth - e.clientX;

  if (distanceFromRight < 120) {
    sidebar.classList.remove("retraida");
  } else {
    sidebar.classList.add("retraida");
  }
});

async function finalizarCompra() {
  const usuario = JSON.parse(localStorage.getItem("usuario"));

  if (!usuario || !usuario.email) {
    alert("Você precisa estar logado para finalizar a compra.");
    return;
  }

  if (carrinho.length === 0) {
    alert("Carrinho vazio.");
    return;
  }

  const pedido = {
    customerEmail: usuario.email,
    items: carrinho
  };

  try {
    const res = await fetch("http://localhost:8080/api/pedidos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(pedido)
    });

    if (res.ok) {
      alert("Pedido enviado com sucesso!");
      carrinho = [];
      atualizarCarrinho();

      document.getElementById("carrinho-sidebar").classList.remove("active");
    } else {
      const msg = await res.text();
      alert("Erro ao enviar pedido:\n" + msg);
    }
  } catch (err) {
    alert("Erro de conexão com o servidor.");
  }
}



    carregarServicos();
    carregarProdutos();