import React, { useState, useEffect } from "react";
import Management from "./components/c/View/Management";

export default function AppComponent(props) {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const [loadingSSO, setLoadingSSO] = useState(false);

  const clientId = "id-ba31bdd5-f1df-3568-b9cf-b63d3e99866";
  const clientSecret = "secret-e6178cb1-c472-557a-601d-6d8e13caceb5";
  const redirectUri = window.location.origin + window.location.pathname;

  useEffect(() => {
    if (window.themeDisplay && !window.themeDisplay.isSignedIn()) {
      localStorage.clear();
      sessionStorage.clear();
      if (token) setToken(null);
      return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");

    if (code && !token) {
      setLoadingSSO(true);

      fetch("/o/oauth2/token", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
          client_id: clientId,
          client_secret: clientSecret,
          grant_type: "authorization_code",
          code: code,
          redirect_uri: redirectUri,
        }),
      })
        .then((res) => res.json())
        .then((data) => {
          if (data.access_token) {
            localStorage.setItem("token", data.access_token);
            setToken(data.access_token);
            window.history.replaceState(null, null, window.location.pathname);
          } else {
            console.error("Errore SSO da Liferay:", data);
          }
          setLoadingSSO(false);
        })
        .catch((err) => {
          console.error("Errore di rete SSO:", err);
          setLoadingSSO(false);
        });
    }
  }, [token]);

  const loginWithLiferay = () => {
    window.location.href = `/o/oauth2/authorize?client_id=${clientId}&response_type=code&redirect_uri=${redirectUri}`;
  };

  const handleLogout = () => {
    localStorage.clear();
    sessionStorage.clear();
    setToken(null);
    window.location.href = "/c/portal/logout";
  };


  const liferayNukeCss = `
    
    #banner, #footer { display: none !important; }
    
    #wrapper, #content, .portlet-layout, .portlet-column { 
      padding: 0 !important; 
      margin: 0 !important; 
      max-width: 100% !important; 
    }
    
    .portlet-decorate .portlet-content { border: none !important; background: transparent !important; }
    .portlet-title-text { display: none !important; }
  `;

  if (!token) {
    return (
      <>
        <style>{liferayNukeCss}</style>
        <div style={{ display: "flex", flexDirection: "column", height: "100vh", fontFamily: "sans-serif", backgroundColor: "#fff" }}>

          <header style={{ display: "flex", alignItems: "center", padding: "20px 5%", borderBottom: "1px solid #ddd" }}>
            <h1 style={{ fontSize: "2rem", fontWeight: "900", color: "#000", margin: 0 }}>SneakVerse</h1>
          </header>

          <div style={{ display: "flex", flex: "1", alignItems: "center", justifyContent: "space-between", padding: "0 5%", maxWidth: "1200px", margin: "0 auto", width: "100%", boxSizing: "border-box" }}>

            <div style={{ flex: "1", paddingRight: "40px" }}>
              <h1 style={{ fontSize: "3.5rem", fontWeight: "900", lineHeight: "1.1", marginBottom: "20px", color: "#111", letterSpacing: "-1px" }}>
                Gestisci.<br />
                <span style={{ color: "#7a8b99" }}>Supervisiona.</span>
              </h1>
              <p style={{ color: "#666", fontSize: "1.1rem", marginBottom: "30px", maxWidth: "80%", lineHeight: "1.5" }}>
                {loadingSSO ? "" : "Accedi al Pannello di Controllo per gestire l'intero ecosistema SneakVerse in tempo reale."}
              </p>

              {!loadingSSO && (
                <button
                  onClick={loginWithLiferay}
                  style={{ padding: "14px 32px", fontSize: "16px", fontWeight: "bold", cursor: "pointer", backgroundColor: "#000", color: "#fff", border: "none", borderRadius: "30px" }}
                >
                  Dashboard
                </button>
              )}
            </div>


          </div>
        </div>
      </>
    );
  }

  return (
    <>
      <style>{liferayNukeCss}</style>
      <Management token={token} onLogout={handleLogout} liferayProps={props} />
    </>
  );
}