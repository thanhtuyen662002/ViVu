"use client";

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";
// import { useLanguage } from "@/contexts/LanguageContext";
import { Separator } from "../ui/separator";
import { useLanguage } from "@/contexts/LanguageContext";

export interface Locations {
  id: number;
  nameVi: string;
  nameEn: string;
  descriptionVi: string;
  descriptionEn: string;
  regionVi: string;
  regionEn: string;
  countryVi: string;
  countryEn: string;
  imageUrl: string;
}

export function LocationInfoCard(data: Locations) {  
  const { t, language } = useLanguage();

  return (
    <div className="relative w-screen h-screen group overflow-x-hidden h-[calc(100vh-14vh)]">
      {/* Background Image with Gradient Overlay */}
      <div
        className="absolute inset-0 bg-cover bg-center"
        style={{ backgroundImage: `url(${data.imageUrl})` }}
      >
        <div className="absolute inset-0 bg-gradient-to-t from-black/90 via-black/50 to-transparent" />
      </div>

      {/* Content */}
      <Card className="relative w-full h-full bg-transparent border-none shadow-none flex flex-col justify-end pb-10">
        <CardHeader className="flex flex-col items-start pt-[35vh]">
          <CardTitle className="text-4xl font-bold text-white mb-2">
            {language === "vi" ? data.nameVi : data.nameEn}
          </CardTitle>
          <CardDescription className="text-lg text-gray-200 max-w-2xl text-left">
            {language === "vi" ? data.descriptionVi : data.descriptionEn}
          </CardDescription>
        </CardHeader>
        <CardContent className="flex flex-col space-y-6 text-white">
          <div className="space-y-4 max-w-2xl">
            <div className="flex">
              <div className="flex items-end">
                <Label htmlFor="name" className="text-gray-200 text-md mr-2">
                  {t("region")}
                </Label>
                <p id="name" className="text-lg font-medium">
                  {language === "vi" ? data.regionVi : data.regionEn}
                </p>
              </div>
              <Separator orientation="vertical" className="mx-4" />
              <div className="flex items-end">
                <Label
                  htmlFor="description"
                  className="text-gray-200 text-md mr-2"
                >
                  {t("country")}
                </Label>
                <p id="description" className="text-lg font-medium">
                  {language === "vi" ? data.countryVi : data.countryEn}
                </p>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
