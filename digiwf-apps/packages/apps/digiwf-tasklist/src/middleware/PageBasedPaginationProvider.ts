import {PageId} from "./pageId";


export const DEFAULT_PAGE = 0;
export const DEFAULT_SIZE = 20;

export class PageBasedPaginationProvider {
  private pageKeyToPaginationData: PageKeyToPaginationDataMap = new Map();
  public getPaginationDataInSession(pageId: PageId): PaginationDataInSession | undefined {
    return this.pageKeyToPaginationData.get(pageId)
  }

  public setPageOfPageId(pageId: PageId, page: number) {
    const paginationInformationOfPage = this.pageKeyToPaginationData.get(pageId);
    this.pageKeyToPaginationData?.set(pageId, {
      size: paginationInformationOfPage?.size || DEFAULT_SIZE,
      page,
      searchQuery: paginationInformationOfPage?.searchQuery
    })
  }

  public setSizeOfPageId = (pageId: PageId, size: number) => {
    const paginationInformationOfPage = this.pageKeyToPaginationData.get(pageId);
    this.pageKeyToPaginationData.set(pageId, {
      size,
      page: paginationInformationOfPage?.page || DEFAULT_PAGE,
      searchQuery: paginationInformationOfPage?.searchQuery
    })
  }
  public setSearchQuery = (pageId: PageId, searchQuery: string | undefined) => {
    const paginationInformationOfPage = this.pageKeyToPaginationData.get(pageId);
    this.pageKeyToPaginationData?.set(pageId, {
      size: paginationInformationOfPage?.size || DEFAULT_SIZE,
      page: paginationInformationOfPage?.page || DEFAULT_PAGE,
      searchQuery
    })
  }
}


export interface PaginationDataInSession {
  readonly searchQuery?: string;
  readonly page: number;
  readonly size: number;
}

export type PageKeyToPaginationDataMap = Map<PageId, PaginationDataInSession>;
