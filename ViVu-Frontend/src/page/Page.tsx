import StartPage from "@/components/custom/StartPage";
import Header from "@/components/custom/Header";
import Footer from "@/components/custom/Footer";
import { AppSidebar } from "@/components/custom/SideBar";
import { SidebarProvider } from "@/components/ui/sidebar";

export default function Page() {
  return (
    <>
      <SidebarProvider>
        <AppSidebar />
        <div className="w-full">
          <Header />
          <StartPage />
        </div>
      </SidebarProvider>
      <Footer />
    </>
  );
}
