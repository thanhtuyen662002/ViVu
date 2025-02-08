import { Skeleton } from "@/components/ui/skeleton";
import { Card, CardContent, CardHeader } from "../ui/card";

export function SkeletonCard() {
  return (
    <Card className="group relative h-full max-w-sm transition-all hover:shadow-lg">
      <CardHeader className="p-0">
        <Skeleton className="h-48 w-full rounded-xl" />
      </CardHeader>
      <CardContent className="space-y-4 p-4 h-48">
        <div className="flex items-center space-x-4">
          <Skeleton className="h-6 w-6"/>
          <Skeleton className="h-6 w-48"/>
        </div>
        <div className="flex items-center space-x-4">
          <Skeleton className="h-6 w-6"/>
          <Skeleton className="h-6 w-48"/>
        </div>
        <div className="flex items-center space-x-4">
          <Skeleton className="h-6 w-6"/>
          <Skeleton className="h-6 w-48"/>
        </div>
        <Skeleton className="h-8 w-48"/>
      </CardContent>
    </Card>
  );
}
