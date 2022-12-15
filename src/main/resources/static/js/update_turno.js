window.addEventListener("load", function () {
  //Buscamos y obtenemos el formulario donde estan
  //los datos que el usuario pudo haber modificado del turno
  const formulario = document.querySelector("#update_turno_form");
  formulario.addEventListener("submit", function (event) {
    let turnoId = document.querySelector("#id").value;

    //creamos un JSON que tendrá los datos del turno
    //a diferencia de un turno nuevo en este caso enviamos el id
    //para poder identificarlo y modificarlo para no cargarlo como nuevo
    const formData = {
      id: document.querySelector("#id").value,
      fecha: document.querySelector("#fecha").value,
      paciente_id: document.querySelector("#paciente_id").value,
      odontologo_id: document.querySelector("#odontologo_id").value,
    };

    //invocamos utilizando la función fetch la API turnos con el método PUT
    //que modificará al turno que enviaremos en formato JSON
    const url = "/turnos";
    const settings = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    };
    fetch(url, settings).then((response) => response.json());
  });
});

//Es la funcion que se invoca cuando se hace click sobre el id de un turno del listado
//se encarga de llenar el formulario con los datos del estudiante
//que se desea modificar}
function findBy(id) {
  const url = "/turnos" + "/" + id;
  const settings = {
    method: "GET",
  };
   fetch(url, settings)
     .then((response) => response.json())
     .then((data) => {
       let turno = data;
       document.querySelector("#id").value = turno.id;
       document.querySelector("#fecha").value = turno.fecha;
       document.querySelector("#paciente_id").value = turno.paciente_id;
       document.querySelector("#odontologo_id").value = turno.odontologo_id;

       //el formulario por default esta oculto y al editar se habilita
       document.querySelector("#div_turno_updating").style.display = "block";
     })
     .catch((error) => {
       alert("Error: " + error);
     });
}
