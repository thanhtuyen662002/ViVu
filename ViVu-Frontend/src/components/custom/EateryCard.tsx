"use client";

import { Heart, MapPinned, Star, Store } from "lucide-react";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { useLanguage } from "@/contexts/LanguageContext";

export interface Eatery {
  id: number;
  nameVi: string;
  nameEn: string;
  addressVi: string;
  addressEn: string;
  imageUrl: string;
  rating: string;
  priceRangeVi: string;
  priceRangeEn: string;
  typeVi: string;
  typeEn: string;
}

export default function PlacesCard(data: Eatery) {
  const { t, language } = useLanguage();
  return (
    <Card className="group relative w-full h-full max-w-sm transition-all hover:shadow-lg">
      <CardHeader className="p-0">
        <div className="relative h-48 w-full overflow-hidden">
          <img
            src={data.imageUrl || "/placeholder.svg"}
            alt={data.nameVi}
            className="object-cover transition-transform group-hover:scale-105 rounded-t-md group-hover:rounded-t-md"
          />
          <button className="absolute right-3 top-3 rounded-full bg-white/80 p-2 backdrop-blur-sm transition-colors hover:bg-white">
            <Heart className="h-5 w-5 text-red-500" />
          </button>
        </div>
      </CardHeader>
      <CardContent className="space-y-4 p-4">
        <div>
          <h3 className="line-clamp-2 font-medium leading-tight">
            {language === "vi" ? data.nameVi : data.nameEn}
          </h3>
          <div className="mt-2 space-y-2 text-sm text-left text-muted-foreground">
            <div className="flex items-start gap-2">
              <span>
                <MapPinned className="h-4 w-4 mt-0.5" />
              </span>{" "}
              {language === "vi" ? data.addressVi : data.addressEn}
            </div>
            <div className="flex">
              <div className="flex w-1/2 items-center gap-2">
                <span>
                  <Star className="h-4 w-4" />
                </span>{" "}
                {t("rating")}
                {language === "vi" ? data.rating : data.rating}
              </div>
              <div className="flex w-1/2 items-center gap-2">
                <span>
                  <Store className="h-4 w-4" />
                </span>{" "}
                {t("type")}
                {language === "vi" ? data.typeVi : data.typeEn}
              </div>
            </div>
          </div>
          <div className="text-lg font-bold text-red-600 text-start">
            {language === "vi" ? data.priceRangeVi : data.priceRangeEn}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
