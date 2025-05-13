document.getElementById("registerForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const form = e.target;
  const nome = form.nome.value.trim();
  const email = form.email.value.trim();
  const senha = form.senha.value;
  const telefone = form.telefone?.value.trim() || "";

  if (!nome || !email || !senha) {
    alert("Por favor, preencha todos os campos obrigatórios.");
    return;
  }

  const userPayload = {
    name: nome,
    email: email,
    password: senha,
    phone: telefone,
    type: "TUTOR"
  };
console.log("Payload enviado para o backend:", userPayload);

  try {
    const userRes = await fetch("http://localhost:8080/api/usuarios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userPayload)
    });

    if (!userRes.ok) {
      const errorText = await userRes.text();
      alert("Erro ao cadastrar usuário:\n" + errorText);
      return;
    }


    window.location.href = "login.html";

  } catch (err) {
    console.error("Erro no cadastro:", err);
    alert("Erro ao conectar com o servidor.");
  }
});
