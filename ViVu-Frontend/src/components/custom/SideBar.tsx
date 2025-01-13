"use client";
import Logo from "@/assets/ViVu-Logo.png";
import { useLanguage } from "@/contexts/LanguageContext";
import * as React from "react";
import {
  Sidebar,
  SidebarContent,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarRail,
} from "@/components/ui/sidebar";
import { History, Home, Hotel, Soup } from "lucide-react";
import { NavMain } from "./NavMain";
import { NavFavorite } from "./NavFavorite";

const data = {
    navMain: [
      {
        title: "home",
        url: "#",
        icon: Home,
        isActive: true,
      },
      {
        title: "history",
        url: "#",
        icon: History,
      },
    ],
    navFavorite: [
        {
          title: "hotels",
          url: "#",
          icon: Hotel,
        },
        {
          title: "foods",
          url: "#",
          icon: Soup,
        },
      ],
  }

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  const { t } = useLanguage();

  // Dịch dữ liệu
  const translatedData = {
    navMain: data.navMain.map((navItem) => ({
      ...navItem,
      title: t(navItem.title),
    })),
    navFavorite: data.navFavorite.map((navItem) => ({
      ...navItem,
      title: t(navItem.title),
    })),
  };

  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" asChild>
              <div className="flex items-center gap-4">
                <img src={Logo} alt="Logo" className="h-10 w-10" />
                <div className="font-bold text-lg font-mono">{t("logo")}</div>
              </div>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
      <SidebarContent>
      <NavMain items={translatedData.navMain} />
      <NavFavorite items={translatedData.navFavorite} />
      </SidebarContent>
      <SidebarRail />
    </Sidebar>
  );
}
