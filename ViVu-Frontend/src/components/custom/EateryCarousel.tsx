"use client";

import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";
import EateryCard, { Eatery } from "./EateryCard";
import { SkeletonCard } from "./SkeletonCard";

export default function EateryCarousel({
  data,
  loading,
}: {
  data: Eatery[];
  loading: boolean;
}) {
  return (
    <Carousel className="w-full max-w-7xl mx-auto">
      <CarouselContent className="-ml-2 md:-ml-4">
        {loading ? data.map((data, index) => (
          <CarouselItem
            key={index}
            className="pl-2 md:pl-4 sm:basis-1 md:basis-1/2 lg:basis-1/4"
          >
            <EateryCard {...data} />
          </CarouselItem>
        )) : (
          <CarouselItem
            className="pl-2 flex justify-center items-center space-x-6"
          >
            <SkeletonCard />
            <SkeletonCard />
            <SkeletonCard />
            <SkeletonCard />
          </CarouselItem>
        )}
      </CarouselContent>
      <CarouselPrevious />
      <CarouselNext />
    </Carousel>
  );
}
