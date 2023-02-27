import {useRouter} from "vue-router/composables";
import {Ref, ref} from "vue";

const DEFAULT_PAGE = 0;
const DEFAULT_SIZE = 20;

interface PaginationData {
  readonly searchQuery: Ref<string | undefined>;
  readonly page: Ref<number>;
  readonly size: Ref<number>;
  readonly setSearchQuery: (searchFilter?: string) => void;
  readonly setPage: (page: number) => void;
  readonly setSize: (size: number) => void;
  readonly getSearchQueryOfUrl: () => string | undefined;
}

export const useGetPaginationData = (): PaginationData => {
  const router = useRouter();

  const getPageOfUrl = (): number => {
    const pageString = router.currentRoute.query?.page as string | null;
    if (!!pageString && !isNaN(parseInt(pageString))) {
      return parseInt(pageString);
    }
    return DEFAULT_PAGE;
  }
  const getSizeOfUrl = (): number => {
    const sizeString = router.currentRoute.query?.size as string | null;
    if (!!sizeString && !isNaN(parseInt(sizeString))) {
      return parseInt(sizeString);
    }
    return DEFAULT_SIZE;
  }
  const getSearchQueryOfUrl = (): string | undefined => {
    const queryFilterValue = router.currentRoute.query?.filter as string | null
    return !!queryFilterValue ? queryFilterValue : undefined;
  }

  const searchQuery = ref<string | undefined>(getSearchQueryOfUrl());
  const page = ref<number>(getPageOfUrl());
  const size = ref<number>(getSizeOfUrl());

  const setPage = (newPage: number) => {
    page.value = newPage;
    router.replace({
      query: {
        ...router.currentRoute.query,
        page: page.value?.toString(),
      }
    });
  }
  const setSize = (newSize: number) => {
    size.value = newSize;
    router.replace({
      query: {
        ...router.currentRoute.query,
        size: size.value?.toString(),
      }
    });
  }
  const setSearchQuery = (newSearchQuery?: string) => {
    searchQuery.value = newSearchQuery
    router.replace({
      query: {
        ...router.currentRoute.query,
        filter: newSearchQuery
      }
    });
    setPage(0);
  }
  return {
    searchQuery,
    page,
    size,
    setPage,
    setSize,
    setSearchQuery,
    getSearchQueryOfUrl,
  }
}
