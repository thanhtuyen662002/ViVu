import Logo from "@/assets/LogoWithName.png";
import { useLanguage } from "@/contexts/LanguageContext";

export default function StartPage() {
    const { t } = useLanguage();
    return (
        <div className="flex items-center justify-center flex-col min-h-[calc(100svh-4rem)] max-w-full overflow-hidden">
            <img src={Logo} className="h-1/3 w-1/3"/>
            <p className="text-lg text-gray-500 text-md">{t("guideline")}</p>
        </div>
    );
}