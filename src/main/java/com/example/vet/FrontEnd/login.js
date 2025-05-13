document.getElementById("loginForm").onsubmit = async function (e) {
  e.preventDefault();

  const form = e.target;

  const loginPayload = {
    email: form.email.value,
    password: form.password.value
  };

  try {
    const res = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginPayload)
    });

    const json = await res.json();

    if (json.success) {
      const usuario = {
        nome: json.nome,
        tipo: json.tipo,
        email: form.email.value
      };
      localStorage.setItem("usuario", JSON.stringify(usuario));

      if (json.tipo === "TUTOR") {
        const tutorRes = await fetch(`http://localhost:8080/api/tutores/email/${usuario.email}`);
        if (tutorRes.ok) {
          const tutorData = await tutorRes.json();
          localStorage.setItem("tutor", JSON.stringify(tutorData));
        }
      }


        window.location.href = "index.html";


    } else {
      alert("Email ou senha incorretos.");
    }

  } catch (error) {
    console.error("Erro no login:", error);
    alert("Erro ao tentar fazer login. Tente novamente mais tarde.");
  }
};
