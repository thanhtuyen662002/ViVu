"use client";
import { useLanguage } from "@/contexts/LanguageContext";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "../ui/textarea";

export function SearchForm() {
  const { t } = useLanguage();

  const formSchema = z.object({
    location: z.string().trim().nonempty({ message: t("locationMess")}),
    budget: z.string(),
    demand: z.string(),
    requirement: z.string(),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      location: "",
      budget: "",
      demand: "",
      requirement: "",
    },
  });

  function onSubmit(values: z.infer<typeof formSchema>) {
    console.log(values);
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-2">
        <FormField
          control={form.control}
          name="location"
          render={({ field }) => (
            <>
              <FormItem>
                <FormLabel>
                  {t("location")}{" "}
                  <span className="text-red-500 font-bold">*</span>
                </FormLabel>
                <FormControl>
                  <Input placeholder={t("locationExp")} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            </>
          )}
        />
        <FormField
          control={form.control}
          name="budget"
          render={({ field }) => (
            <>
              <FormItem>
                <FormLabel>{t("budget")}</FormLabel>
                <FormControl>
                  <Input placeholder={t("budgetExp")} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            </>
          )}
        />
        <FormField
          control={form.control}
          name="demand"
          render={({ field }) => (
            <>
              <FormItem>
                <FormLabel>{t("demand")}</FormLabel>
                <FormControl>
                  <Input placeholder={t("demandExp")} {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            </>
          )}
        />
        <FormField
          control={form.control}
          name="requirement"
          render={({ field }) => (
            <>
              <FormItem>
                <FormLabel>{t("requirement")}</FormLabel>
                <FormControl>
                  <Textarea
                    placeholder={t("requirementExp")}
                    {...field}
                    className="h-32 resize-none"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            </>
          )}
        />
        <div className="flex justify-end">
          <Button type="submit">{t("searchButton")}</Button>
        </div>
      </form>
    </Form>
  );
}
