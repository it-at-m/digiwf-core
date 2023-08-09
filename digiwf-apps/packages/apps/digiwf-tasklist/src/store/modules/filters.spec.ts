import {filters, FilterState} from "./filters";

describe("filters", () => {
  describe("getters:getSortDirectionOfPage", () => {
    it("should return sort direction of correct page", () => {
      const state: FilterState = {
        general: {
          a: {
            sortDirection: "-a"
          },
          b: {
            sortDirection: "-b"
          },
        }
      };

      const resultA = filters.getters.getSortDirectionOfPage(state)("a");
      expect(resultA).toBe("-a");
      const resultB = filters.getters.getSortDirectionOfPage(state)("b");
      expect(resultB).toBe("-b");
    });
    it("should return default sort direction if page is not saved", () => {
        const state: FilterState = {
          general: {
            a:
              {
                sortDirection: "-a"
              }
          }
        };

        const resultB = filters.getters.getSortDirectionOfPage(state)("b");
        expect(resultB).toBe("-createTime");
      }
    );

  });

  describe("mutations", () => {
    it("should set sort direction to existing entry", () => {
      const state: FilterState = {
        general: {
          a: {

            sortDirection: "-a"
          },
          b: {
            sortDirection: "-b"
          }
        }
      };
      filters.mutations.setSortDirectionOfPage(state, {pageId: "a", sortDirection: "new"});
      expect(state.general["a"]?.sortDirection).toBe("new");
      expect(state.general["b"]?.sortDirection).toBe("-b");

    });
    it("should set sort direction to new entry", () => {
      const state: FilterState = {
        general: {
          a: {
            sortDirection: "-a"
          }
        }
      };
      filters.mutations.setSortDirectionOfPage(state, {pageId: "c", sortDirection: "new"});
      expect(state.general["a"]?.sortDirection).toBe("-a");
      expect(state.general["c"]?.sortDirection).toBe("new");
    });

  });
});
