import { useRouter } from "vue-router/composables";
import { inject, ref, Ref } from "vue";
import { usePageId } from "./pageId";
import { DEFAULT_PAGE, DEFAULT_SIZE, PageBasedPaginationProvider } from "./PageBasedPaginationProvider";

interface PaginationData {
  readonly searchQuery: Ref<string | undefined>;
  readonly page: Ref<number>;
  readonly size: Ref<number>;
  readonly setSearchQuery: (searchFilter?: string) => void;
  readonly setPage: (page: number) => void;
  readonly setSize: (size: number) => void;
  readonly getSearchQueryOfUrl: () => string | undefined;
  readonly tag: Ref<string | undefined>;
  readonly setTag: (tag: string) => void;
}

export const useGetPaginationData = (): PaginationData => {
  const router = useRouter();
  const pageId = usePageId();
  const pageKeyToPaginationData = inject<PageBasedPaginationProvider>("paginationData");
  if (!pageKeyToPaginationData) {
    throw Error("could not inject PageBasedPaginationProvider");
  }

  const paginationInformationOfPage = pageKeyToPaginationData.getPaginationDataInSession(pageId.id || "unknown");
  const getDefaultPage = (): number => {
    const pageString = router.currentRoute.query?.page as string | null;
    if (!!pageString && !isNaN(parseInt(pageString))) {
      return parseInt(pageString);
    }
    return paginationInformationOfPage?.page || DEFAULT_PAGE;
  };
  const getDefaultSize = (): number => {
    const sizeString = router.currentRoute.query?.size as string | null;
    if (!!sizeString && !isNaN(parseInt(sizeString))) {
      return parseInt(sizeString);
    }
    return paginationInformationOfPage?.size || DEFAULT_SIZE;
  };
  const getSearchQueryOfUrl = (): string | undefined => {
    const queryFilterValue = router.currentRoute.query?.filter as string | null;
    if (queryFilterValue) {
      return queryFilterValue;
    }
    return paginationInformationOfPage?.searchQuery ? paginationInformationOfPage?.searchQuery : undefined;
  };
  const getTagOfUrl = (): string | undefined => {
    const queryTagValue = router.currentRoute.query?.tag as string | null;
    if (queryTagValue) {
      return queryTagValue;
    }
    return paginationInformationOfPage?.tag ? paginationInformationOfPage?.tag : undefined;
  };

  const searchQuery = ref<string | undefined>(getSearchQueryOfUrl());
  const page = ref<number>(getDefaultPage());
  const size = ref<number>(getDefaultSize());
  const tag = ref<string | undefined>(getTagOfUrl());
  const setPage = (newPage: number) => {
    page.value = newPage;
    router.replace({
      query: {
        ...router.currentRoute.query,
        page: page.value?.toString(),
      }
    });
    pageKeyToPaginationData.setPageOfPageId(pageId.id, newPage);
  };
  const setSize = (newSize: number) => {
    size.value = newSize;
    // reset the page to 0, because the user should see the first page after changing the size
    setPage(DEFAULT_PAGE);
    router.replace({
      query: {
        ...router.currentRoute.query,
        size: size.value?.toString(),
      }
    });
    pageKeyToPaginationData.setSizeOfPageId(pageId.id, newSize);
  };
  const setSearchQuery = (newSearchQuery?: string) => {
    searchQuery.value = newSearchQuery;
    router.replace({
      query: {
        ...router.currentRoute.query,
        filter: newSearchQuery
      }
    });
    // jump back to first page, so that user can see the first results again
    setPage(0);
    pageKeyToPaginationData.setSearchQuery(pageId.id, searchQuery.value);
  };

  const setTag = (newTag?: string) => {
    tag.value = newTag;
    router.replace({
      query: {
        ...router.currentRoute.query,
        tag: newTag
      }
    });
    // jump back to first page, so that user can see the first results again
    setPage(0);
    pageKeyToPaginationData.setTag(pageId.id, tag.value);
  };

  // load pagination from session after page switch
  if (paginationInformationOfPage) {
    setSearchQuery(paginationInformationOfPage.searchQuery);
    setTag(paginationInformationOfPage.tag);
    setSize(paginationInformationOfPage.size);
    // set page must be the last mutation because the upper ones has impact of the page
    setPage(paginationInformationOfPage.page);
  }

  return {
    searchQuery,
    page,
    size,
    setPage,
    setSize,
    setSearchQuery,
    getSearchQueryOfUrl,
    tag,
    setTag,
  };
};
