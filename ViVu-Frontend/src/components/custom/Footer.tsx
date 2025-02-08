import { useLanguage } from "@/contexts/LanguageContext";

export default function Footer() {
  const { t } = useLanguage();
  return (
    <footer>
      <div className="flex items-center justify-center text-primary bg-background bottom-0 fixed w-full h-10 text-sm font-medium">
        <p>&copy; {t("footer")}</p>
      </div>
    </footer>
  );
}
