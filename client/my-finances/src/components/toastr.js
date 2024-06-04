import toastr from "toastr";

toastr.options = {
  "closeButton": true,
  "debug": false,
  "newestOnTop": true,
  "progressBar": true,
  "positionClass": "toast-bottom-full-width",
  "preventDuplicates": true,
  "onclick": null,
  "showDuration": "300",
  "hideDuration": "1000",
  "timeOut": "5000",
  "extendedTimeOut": "1000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
}

const notificar = (titulo, mensagem, tipo) => {
    toastr[tipo](mensagem, titulo);
}

export const mensagemError = (mensagem) => {
    notificar("Error", mensagem, "error");
}

export const mensagemSucesso = (mensagem) => {
    notificar("sucesso", mensagem, "=success");
}

export const mensagemInfo = (mensagem) => {
    notificar("sucesso", mensagem, "info");
}

export const mensagemAlerta = (mensagem) => {
    notificar("Atenção", mensagem, "warning");
}
