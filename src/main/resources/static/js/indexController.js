function carregarSugestaoMusica() {
    const tagSugestao = document.getElementById("sugestao");
    const endpoint = "http://localhost:8080/apis/find-musics?chave=";

    fetch(endpoint)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Nenhuma música cadastrada.");
        })
        .then(musicas => {
            if (musicas.length > 0) {
                const sorteada = musicas[Math.floor(Math.random() * musicas.length)];
                tagSugestao.innerHTML = `Música do Dia: <strong>${sorteada.titulo}</strong> - ${sorteada.artista} (${sorteada.estilo})`;
            } else {
                tagSugestao.innerHTML = "Nenhuma música cadastrada ainda.";
            }
        })
        .catch(error => {
            tagSugestao.innerHTML = "Bem-vindo ao PlayMySongs!";
        });
}