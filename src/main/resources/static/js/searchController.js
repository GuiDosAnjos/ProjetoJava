function pesquisarMusicas() {
    const chave = document.getElementById("chave").value;
    const divResultado = document.getElementById("resultado");
    const endpoint = `http://localhost:8080/apis/find-musics?chave=${encodeURIComponent(chave)}`;

    divResultado.innerHTML = '<p class="text-muted">A pesquisar...</p>';

    fetch(endpoint)
        .then(response => {
            return response.json().then(data => ({ ok: response.ok, data }));
        })
        .then(res => {
            if (!res.ok) {
                divResultado.innerHTML = `<div class="alert alert-warning">${res.data.mens}</div>`;
                return;
            }

            let html = '<div class="list-group">';
            res.data.forEach(musica => {
                html += `
                    <div class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                        <div>
                            <h5 class="mb-1">${musica.titulo}</h5>
                            <p class="mb-1 text-muted">Artista: ${musica.artista} | Estilo: ${musica.estilo}</p>
                        </div>
                        <div class="mt-2 mt-md-0">
                            <audio controls>
                                <source src="${musica.nomeArquivo}" type="audio/mpeg">
                                O seu navegador não suporta o reprodutor de áudio.
                            </audio>
                        </div>
                    </div>
                `;
            });
            html += '</div>';

            divResultado.innerHTML = html;
        })
        .catch(error => {
            divResultado.innerHTML = `<div class="alert alert-danger">Erro ao efetuar a pesquisa.</div>`;
        });
}