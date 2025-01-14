import "./App.css";
import { LanguageProvider } from "./contexts/LanguageContext";

import Page from "@/page/Page";

function App() {
  return (
    <div>
      <LanguageProvider>
        <Page />
      </LanguageProvider>
    </div>
  );
}

export default App;
