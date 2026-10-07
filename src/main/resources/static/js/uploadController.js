function carregarEstilos() {
    const selectEstilo = document.getElementById("estilo");
    const endpoint = "http://localhost:8080/apis/get-music-styles";

    fetch(endpoint)
        .then(response => response.json())
        .then(estilos => {
            selectEstilo.innerHTML = '<option value="">Selecione um estilo</option>';
            estilos.forEach(estilo => {
                selectEstilo.innerHTML += `<option value="${estilo}">${estilo}</option>`;
            });
        })
        .catch(error => {
            selectEstilo.innerHTML = '<option value="">Erro ao carregar estilos</option>';
        });
}

function enviarMusica(event) {
    event.preventDefault();

    const fileInput = document.getElementById("file");
    const file = fileInput.files[0];

    // Validação por JS no formato .mp3 ou .ogg
    if (!file) {
        alert("Selecione um ficheiro de áudio!");
        return;
    }

    const extensao = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
    if (extensao !== ".mp3" && extensao !== ".ogg") {
        alert("Formato inválido! Por favor envie um ficheiro .mp3 ou .ogg");
        return;
    }

    const formData = new FormData();
    formData.append("titulo", document.getElementById("titulo").value);
    formData.append("artista", document.getElementById("artista").value);
    formData.append("estilo", document.getElementById("estilo").value);
    formData.append("file", file);

    const divMensagem = document.getElementById("mensagem");
    divMensagem.innerHTML = '<div class="alert alert-info">A enviar áudio...</div>';

    fetch("http://localhost:8080/apis/music-upload", {
        method: "POST",
        body: formData
    })
    .then(response => {
        return response.json().then(data => ({ ok: response.ok, data }));
    })
    .then(res => {
        if (res.ok) {
            divMensagem.innerHTML = `<div class="alert alert-success">Música "${res.data.titulo}" cadastrada com sucesso!</div>`;
            document.getElementById("formUpload").reset();
        } else {
            divMensagem.innerHTML = `<div class="alert alert-danger">Erro: ${res.data.mens}</div>`;
        }
    })
    .catch(error => {
        divMensagem.innerHTML = `<div class="alert alert-danger">Erro na conexão com o servidor.</div>`;
    });
}