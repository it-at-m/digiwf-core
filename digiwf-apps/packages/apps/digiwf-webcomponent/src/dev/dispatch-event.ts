function dispatchAccessTokenEvent() {
  const uuid = crypto.randomUUID();
  document.dispatchEvent(
    new CustomEvent("access-token-loaded", {
      detail: {
        accessToken: `randomAccessToken-${uuid}`,
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
