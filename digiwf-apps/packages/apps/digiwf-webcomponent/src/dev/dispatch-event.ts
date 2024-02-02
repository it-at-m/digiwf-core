const dispatchEvent = () => {
  document.dispatchEvent(
    new CustomEvent("access-token-loaded", {
      detail: {
        accessToken: "randomAccessToken",
      },
    })
  );
};

setTimeout(() => {
  dispatchEvent();
  setInterval(
    () => {
      dispatchEvent();
    },
    10 * 60 * 1000
  );
}, 5000);
