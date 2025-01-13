import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Search } from "lucide-react";
import { useLanguage } from "@/contexts/LanguageContext";
import VietnamFlag from "@/assets/vietnam.png";
import UnitedKingdom from "@/assets/united-kingdom.png";
import { SidebarTrigger } from "@/components/ui/sidebar";

export default function Header() {
  const { t, setLanguage, language } = useLanguage();
  return (
    <header className="sticky top-0 z-50 flex h-16 w-full items-center justify-between border-b bg-background px-4 shadow-sm md:px-6">
      <div className="flex items-center gap-4">
        <SidebarTrigger className="-ml-1" />
      </div>
      <div>
        <Button variant="default" className="rounded-full px-10">
          <Search />
          {t("input")}
        </Button>
      </div>
      <div className="flex items-center gap-4">
        <div className="flex justify-center align-center">
          <Label
            htmlFor="language-mode"
            onClick={() => setLanguage(language === "vi" ? "en" : "vi")}
            className="cursor-pointer"
          >
            <img
              src={language === "vi" ? VietnamFlag : UnitedKingdom}
              className="h-10 w-10"
            />
          </Label>
        </div>
        <Button variant="default" className="rounded-full">
          {t("signIn")}
        </Button>
      </div>
    </header>
  );
}
