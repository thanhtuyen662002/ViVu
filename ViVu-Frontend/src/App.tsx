import "./App.css";
import Header from "./components/custom/Header";
import Footer from "./components/custom/Footer";
import { LanguageProvider } from "./contexts/LanguageContext";
import { AppSidebar } from "@/components/custom/SideBar";
import { SidebarProvider } from "@/components/ui/sidebar";

function App() {
  return (
    <div>
      <LanguageProvider>
        <SidebarProvider>
          <AppSidebar />
          <Header />
        </SidebarProvider>
        <Footer />
      </LanguageProvider>
    </div>
  );
}

export default App;
