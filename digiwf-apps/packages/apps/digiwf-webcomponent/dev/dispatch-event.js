const accessToken = "ADD_TOKEN_FOR_LOCAL_TESTING_HERE";

function dispatchAccessTokenEvent() {
  document.dispatchEvent(
    new CustomEvent("access-token-loaded", {
      detail: {
        accessToken,
      },
    })
  );
}

setTimeout(() => {
  dispatchAccessTokenEvent();
  setInterval(
    () => {
      dispatchAccessTokenEvent();
    },
    10 * 60 * 1000
  );
}, 5000);
