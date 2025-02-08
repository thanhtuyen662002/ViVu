"use client";

import { Heart, ScrollText, TentTree } from "lucide-react";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { useLanguage } from "@/contexts/LanguageContext";

export interface Places {
  id: number;
  nameVi: string;
  nameEn: string;
  descriptionVi: string;
  descriptionEn: string;
  noteVi: string;
  noteEn: string;
  imageUrl: string;
}

export default function PlacesCard(data: Places) {
  const {language} = useLanguage();
  return (
    <Card className="group relative w-full h-full max-w-sm transition-all hover:shadow-lg">
      <CardHeader className="p-0">
        <div className="relative h-48 w-full overflow-hidden">
          <img
            src={data.imageUrl || "/placeholder.svg"}
            alt={language === "vi" ? data.nameVi : data.nameEn}
            className="object-cover transition-transform group-hover:scale-105 rounded-t-md group-hover:rounded-t-md"
          />
          <button className="absolute right-3 top-3 rounded-full bg-white/80 p-2 backdrop-blur-sm transition-colors hover:bg-white">
            <Heart className="h-5 w-5 text-red-500" />
          </button>
        </div>
      </CardHeader>
      <CardContent className="space-y-4 p-4">
        <div>
          <h3 className="line-clamp-2 font-medium leading-tight">{language === "vi" ? data.nameVi : data.nameEn}</h3>
          <div className="mt-2 space-y-2 text-sm text-left text-muted-foreground">
            <div className="flex items-start gap-2">
              <span className="mt-1">
                <ScrollText className="h-4 w-4" />
              </span>{" "}
              {language === "vi" ? data.descriptionVi : data.descriptionEn}
            </div>
            <div className="flex items-start gap-2">
              <span>
                <TentTree className="h-4 w-4" />
              </span>{" "}
              {language === "vi" ? data.noteVi : data.noteEn}
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
