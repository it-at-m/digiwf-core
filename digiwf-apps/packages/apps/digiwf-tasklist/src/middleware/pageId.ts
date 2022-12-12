import {useRouter} from "vue-router/composables";

const pathToPageId: { [key: string]: string } = {
  "/mytask": "tasks",
  "/assignedgrouptask": "assignedgrouptasks",
  "/opengrouptask": "opengrouptasks"
}

interface PageId {
  readonly id: string | undefined;
  readonly path: string;
}

/**
 * returns the mapped page id from path.
 */
export const usePageId = (): PageId => {
  const router = useRouter();
  const path = router.currentRoute.path;
  return {
    id: pathToPageId[path] as string | undefined,
    path
  };
}
