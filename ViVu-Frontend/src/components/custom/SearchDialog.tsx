import {
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { SearchForm } from "./SearchFrom";
import { useLanguage } from "@/contexts/LanguageContext";

export function SearchDialog() {
  const { t } = useLanguage();
  return (
    <DialogContent className="sm:max-w-[425px]">
      <DialogHeader>
        <DialogTitle>{t("searchDialogTitle")}</DialogTitle>
        <DialogDescription>
          {t("searchDialogDesc")}
        </DialogDescription>
      </DialogHeader>
      <SearchForm />
    </DialogContent>
  );
}
