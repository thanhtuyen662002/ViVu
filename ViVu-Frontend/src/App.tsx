import "./App.css";
import { LanguageProvider } from "./contexts/LanguageContext";
import { BrowserRouter as Router } from "react-router-dom";
import AppRoutes from "@/config/routes";
import Header from "@/components/custom/Header";
import Footer from "@/components/custom/Footer";

function App() {
  return (
    <div>
      <LanguageProvider>
        <Router>
          <Header />
          <AppRoutes />
          <Footer />
        </Router>
      </LanguageProvider>
    </div>
  );
}

export default App;
