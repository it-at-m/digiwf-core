import {filters, FilterState} from "./filters";

describe("filters", () => {
  describe("getters:getSortDirectionOfPage", () => {
    it("should return sort direction of correct page", () => {
      const state: FilterState = {
        general: [
          {
            pageId: "a",
            sortDirection: "-a"
          },
          {
            pageId: "b",
            sortDirection: "-b"
          },
        ]
      };

      const resultA = filters.getters.getSortDirectionOfPage(state)("a");
      expect(resultA).toBe("-a");
      const resultB = filters.getters.getSortDirectionOfPage(state)("b");
      expect(resultB).toBe("-b");
    });
    it("should return default sort direction if page is not saved", () => {
        const state: FilterState = {
          general: [
            {
              pageId: "a",
              sortDirection: "-a"
            },
          ]
        };

        const resultB = filters.getters.getSortDirectionOfPage(state)("b");
        expect(resultB).toBe("-createTime");
      }
    );

  });

  describe("mutations", () => {
    it("should set sort direction to existing entry", () => {
      const state: FilterState = {
        general: [
          {
            pageId: "a",
            sortDirection: "-a"
          },
          {
            pageId: "b",
            sortDirection: "-b"
          },
        ]
      };
      filters.mutations.setSortDirectionOfPage(state, {pageId: "a", sortDirection: "new"});
      expect(state.general.length).toBe(2);
      expect(state.general.find(it => it.pageId === "a")?.sortDirection).toBe("new");
      expect(state.general.find(it => it.pageId === "b")?.sortDirection).toBe("-b");

    });
    it("should set sort direction to new entry", () => {
      const state: FilterState = {
        general: [
          {
            pageId: "a",
            sortDirection: "-a"
          },
        ]
      };
      filters.mutations.setSortDirectionOfPage(state, {pageId: "c", sortDirection: "new"});
      expect(state.general.length).toBe(2);
      expect(state.general.find(it => it.pageId === "a")?.sortDirection).toBe("-a");
      expect(state.general.find(it => it.pageId === "c")?.sortDirection).toBe("new");
    });

  });
});
